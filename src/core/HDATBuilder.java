package core;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HDATBuilder {

	private HDATBuilder() {

	}

	private static HDATEntry readEntryFromFile(Path filePath, Charset charset) {
		HDATEntry temp = new HDATEntry();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(filePath.toFile()), charset))) {
			String line;
			String section = "";
			while ((line = reader.readLine()) != null) {
				if (line.isEmpty() && !section.startsWith("DATA")) {
					continue;
				}
				if (line.startsWith("[")) {
					section = line.trim().substring(1, line.length() - 1);
					continue;
				}
				switch (section) {
				case "":
					String[] data = line.split("=");
					switch (data[0]) {
					case "name":
						temp.setName(data[1]);
						break;
					case "folderName":
						if (data.length > 1) {
							temp.setFolderName(data[1]);
						} else {
							temp.setFolderName("");
						}
						break;
					case "int1":
						temp.setInt1(Integer.parseInt(data[1]));
						break;
					case "int2":
						temp.setInt2(Integer.parseInt(data[1]));
						break;
					}
					break;
				case "DATA1":
					if (temp.data1 == null) {
						temp.data1 = line;
					} else {
						temp.data1 += "\r\n" + line;
					}
					break;
				case "DATA2":
					if (temp.data2 == null) {
						temp.data2 = line;
					} else {
						temp.data2 += "\r\n" + line;
					}
					break;
				case "DATA3":
					if (temp.data3 == null) {
						temp.data3 = line;
					} else {
						temp.data3 += "\r\n" + line;
					}
					break;
				case "DATA4":
					if (temp.data4 == null) {
						temp.data4 = line;
					} else {
						temp.data4 += "\r\n" + line;
					}
					break;
				case "DATA5":
					if (temp.data5 == null) {
						temp.data5 = line;
					} else {
						temp.data5 += "\r\n" + line;
					}
					break;
				case "DATA6":
					if (temp.data6 == null) {
						temp.data6 = line;
					} else {
						temp.data6 += "\r\n" + line;
					}
					break;
				case "DATA7":
					if (temp.data7 == null) {
						temp.data7 = line;
					} else {
						temp.data7 += "\r\n" + line;
					}
					break;
				case "DATA8":
					if (temp.data8 == null) {
						temp.data8 = line;
					} else {
						temp.data8 += "\r\n" + line;
					}
					break;
				case "NEWDATA":
					if (temp.newData == null) {
						temp.newData = line;
					} else {
						temp.newData += "\r\n" + line;
					}
					break;
				case "EXTRAINTS":
					String[] ints = line.split(", ");
					int[] extraInts = new int[ints.length];
					for (int i = 0; i < ints.length; i++) {
						extraInts[i] = Integer.parseInt(ints[i]);
					}
					temp.setExtraInts(extraInts);
					break;
				case "EXTRADATA":
					String[] bytes = line.split(", ");
					byte[] extraData = new byte[bytes.length];
					for (int i = 0; i < bytes.length; i++) {
						extraData[i] = Byte.parseByte(bytes[i]);
					}
					temp.setExtraData(extraData);
					break;
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		cleanUpEntry(temp);
		return temp;
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
		if (!(entries == null)) {
			HDATParser parser = new HDATParser(path, charset);
			parser.writeHDAT(entries);
		}

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
		List<HDATEntry> entries = mergeEntriesFromModList(modListPath.getParent().resolve("ModFiles"), modList, originals, charset);
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

	private static void cleanUpEntry(HDATEntry temp) {
		if (temp.name == null)
			temp.name = "";
		if (temp.folderName == null)
			temp.folderName = "";
		if (temp.data1 == null)
			temp.data1 = "";
		if (temp.data2 == null)
			temp.data2 = "";
		if (temp.data3 == null)
			temp.data3 = "";
		if (temp.data4 == null)
			temp.data4 = "";
		if (temp.data5 == null)
			temp.data5 = "";
		if (temp.data6 == null)
			temp.data6 = "";
		if (temp.data7 == null)
			temp.data7 = "";
		if (temp.data8 == null)
			temp.data8 = "";
	}
}
