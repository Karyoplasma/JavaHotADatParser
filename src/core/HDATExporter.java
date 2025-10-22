package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class HDATExporter {

	private HDATExporter() {

	}

	/**
	 * Exports all files in the HDAT into separate files. Creates a FileList.txt
	 * serving as an index file
	 * 
	 * The output is /export/FileList.txt and /export/files/... based on the
	 * location of the HotA.dat.
	 * 
	 * @param hdatPath The path to the HotA.dat to export
	 * @param charset  The preferred charset to use as an export Charset
	 * @param entries  The list of HDATEntries in the HotA.dat file
	 */
	public static void exportAllFiles(Path hdatPath, Charset charset, List<HDATEntry> entries) {
		Path exportPath = hdatPath.getParent().resolve("export/FileList.txt");
		exportFileList(entries, exportPath, charset);
		exportPath = exportPath.getParent().resolve("Files");
		if (!exportPath.toFile().exists()) {
			try {
				Files.createDirectories(exportPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		for (HDATEntry entry : entries) {	
			try (PrintWriter writer = new PrintWriter(exportPath.resolve(entry.name).toFile(), charset.toString())) {
				writer.println(entry.toExportString());
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
	}

	private static void exportFileList(List<HDATEntry> entries, Path exportPath, Charset charset) {
		if (!exportPath.toFile().exists()) {
			try {
				Files.createDirectories(exportPath.getParent());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		try (PrintWriter writer = new PrintWriter(exportPath.toFile(), charset.toString())) {
			for (HDATEntry entry : entries) {
				writer.println(entry.getName());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Exports all files with matching name in the mod list from the HDAT into
	 * separate files. Creates a ModList.txt containing an overview of exported
	 * files.
	 * 
	 * The output is /export/ModList.txt and /export/ModFiles/... based on the
	 * location of the HotA.dat.
	 * 
	 * @param hdatPath Path to the HotA.dat
	 * @param charset  Desired charset for exporting
	 * @param entries  The list of HDATEntries in the HotA.dat file
	 * @param modList  The set of HDATEntries with matching name to export
	 */
	public static void exportSpecificFiles(Path hdatPath, Charset charset, List<HDATEntry> entries,
			Set<String> modList) {
		Path exportPath = hdatPath.getParent().resolve("export/ModList.txt");
		exportModList(exportPath, charset, modList);
		exportPath = exportPath.getParent().resolve("ModFiles");
		if (!exportPath.toFile().exists()) {
			try {
				Files.createDirectories(exportPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		for (HDATEntry entry : entries) {
			if (modList.contains(entry.getName())) {
				try (PrintWriter writer = new PrintWriter(exportPath.resolve(entry.getName()).toFile(),
						charset.toString())) {
					writer.println(entry.toExportString());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			}
		}
	}

	private static void exportModList(Path exportPath, Charset charset, Set<String> modList) {
		if (!exportPath.toFile().exists()) {
			try {
				Files.createDirectories(exportPath.getParent());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		try (PrintWriter writer = new PrintWriter(exportPath.toFile(), charset.toString())) {
			for (String entry : modList) {
				writer.println(entry);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
