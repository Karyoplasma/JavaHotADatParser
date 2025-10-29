package main;

import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import core.HDATBuilder;
import core.HDATEntry;
import core.HDATExporter;
import core.HDATParser;
import gui.HDATParserGui;

public class HDATParserMain {
	private static void runFromCommandLine(String[] args) {
		HDATParser parser = new HDATParser(Paths.get("res/HotA.dat"), Charset.forName("windows-1251"));
		List<HDATEntry> entries;
		Set<String> exportList;

		String option = args[0];

		switch (option) {
		case "-E":
			if (args.length > 1 && args[1].equals("--c")) {
				parser.setCharset(Charset.forName("windows-1250"));
			}
			System.out.println("Exporting all files from HotA.dat: ");
			entries = parser.parseHDAT();
			HDATExporter.exportAllFiles(parser.getPath(), parser.getCharset(), entries);
			break;

		case "-e":
			if (args.length < 2) {
				System.err.println("-e requires a list of strings");
				System.exit(1);
			}
			if (args.length > 2 && args[2].equals("--c")) {
				parser.setCharset(Charset.forName("windows-1250"));
			}
			exportList = new HashSet<String>();
			for (String file : args[1].split(",")) {
				exportList.add(file.trim());
			}
			System.out.println("Exporting selected files from HotA.dat: " + args[1]);
			entries = parser.parseHDAT();
			HDATExporter.exportSpecificFiles(parser.getPath(), parser.getCharset(), entries, exportList);
			break;

		case "-W":
			if (args.length > 1 && args[1].equals("--c")) {
				parser.setCharset(Charset.forName("windows-1250"));
			}
			System.out.println("Rebuilding HotA.dat from FileList.txt.");
			HDATBuilder.reconstructFromFolder(parser.getPath().getParent().resolve("export/FileList.txt"),
					parser.getCharset());
			break;

		case "-m":
		case "-w":
			if (args.length > 1 && args[1].equals("--c")) {
				parser.setCharset(Charset.forName("windows-1250"));
			}
			System.out.println("Merging original HotA.dat with files from the ModList.txt.");
			entries = parser.parseHDAT();
			HDATBuilder.reconstructFromModList(Paths.get("res/export/ModList.txt"), parser.getCharset(), entries);
			break;

		default:
			System.err.println("Unknown option: " + option);
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			HDATParserGui.launchGUI();
		} else {
			runFromCommandLine(args);
		}
	}
}
