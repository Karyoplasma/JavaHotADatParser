package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class HDATExporter {

	private HDATExporter() {

	}
	
	/**
	 * Exports all files in the HDAT into separate files. Creates a FileList.txt serving as an index file
	 * @param hdatPath
	 * The path to the HotA.dat to export
	 * @param charset
	 * The preferred charset to use as an export Charset
	 * @param entries
	 * The list of HDATEntries to export
	 */
	public static void exportAllFiles(Path hdatPath, Charset charset, List<HDATEntry> entries) {
		Path exportPath = hdatPath.getParent().resolve("export/FileList.txt");
		exportFileList(entries, exportPath, charset);
		exportPath = exportPath.getParent().resolve("Files");
		System.out.println(exportPath);
		for (HDATEntry entry : entries) {
			if (!exportPath.toFile().exists()) {
				try {
					Files.createDirectories(exportPath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
			}				
			//entry.exportToFile(exportPath, charset);
			try (PrintWriter writer = new PrintWriter(exportPath.resolve(entry.name).toFile(), charset.toString())) {
				writer.println(entry.toString());
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

}
