package core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HDATBuilder {

	private HDATBuilder() {

	}

	private static HDATEntry readEntryFromFile(Path filePath, Charset charset) {

		try {
			// String content = Files.readString(filePath, charset);
			String content = new String(Files.readAllBytes(filePath), charset);
			return HDATEntry.fromText(content);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	private static List<HDATEntry> readEntriesFromFileList(Path path, List<String> fileList, Charset charset) {
		List<HDATEntry> entries = new ArrayList<HDATEntry>();
		for (String file : fileList) {
			HDATEntry temp = readEntryFromFile(path.getParent().resolve("files/" + file), charset);
			entries.add(temp);

		}
		return entries;
	}

	/**
	 * Reconstructs a HotA.dat from the FileList.txt. All files in the list need to
	 * be present. The output file will be written to the same folder as the
	 * FileList.txt.
	 * 
	 * @param path    Path to the FileList.txt
	 * @param charset Preferred charset for the HotA.dat file
	 */
	public static void reconstructFromFolder(Path path, Charset charset) {
		List<String> fileList = new ArrayList<String>();
		try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				fileList.add(line.trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		List<HDATEntry> entries = readEntriesFromFileList(path, fileList, charset);
		HDATParser parser = new HDATParser(path, charset);
		parser.writeHDAT(entries);
	}

	/**
	 * Builds a HotA.dat file, replacing all original entries with the files
	 * mentioned in the mod list. The output file will be written to the same folder
	 * as the ModList.txt.
	 * 
	 * @param modListPath Path to the ModList.txt
	 * @param charset     Desired charset in which the HotA.dat will be written
	 * @param originals   The original entries in the HotA.dat
	 */
	public static void reconstructFromModList(Path modListPath, Charset charset, List<HDATEntry> originals) {
		Set<String> modList = new HashSet<String>();
		try (BufferedReader reader = new BufferedReader(new FileReader(modListPath.toFile()))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}
				modList.add(line.trim());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		List<HDATEntry> entries = mergeEntriesFromModList(modListPath.getParent().resolve("ModFiles"), modList,
				originals, charset);
		HDATParser parser = new HDATParser(modListPath, charset);
		parser.writeHDAT(entries);

	}

	private static List<HDATEntry> mergeEntriesFromModList(Path modListPath, Set<String> modList,
			List<HDATEntry> originals, Charset charset) {
		List<HDATEntry> ret = new ArrayList<HDATEntry>();
		for (HDATEntry entry : originals) {
			if (modList.contains(entry.getName())) {
				ret.add(readEntryFromFile(modListPath.resolve(entry.getName()), charset));
			} else {
				ret.add(entry);
			}
		}

		return ret;
	}
}
