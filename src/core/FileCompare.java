package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

/**
 * Just a debug class. ChatGPT wrote this, no guarantees
 */
public class FileCompare {
	
	public static long computeCRC(String filename) throws IOException {
		CRC32 crc = new CRC32();
		try (FileInputStream fis = new FileInputStream(filename)) {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				crc.update(buffer, 0, bytesRead);
			}
		}
		return crc.getValue();
	}
	
	public static void printFirstDifference(java.io.File f1, java.io.File f2) throws IOException {
		try (FileInputStream in1 = new FileInputStream(f1); FileInputStream in2 = new FileInputStream(f2)) {

			long offset = 0;
			int b1, b2;

			while (true) {
				b1 = in1.read();
				b2 = in2.read();

				if (b1 != b2) {
					System.out.printf("Files differ at offset %d (0x%X): %02X vs %02X%n", offset, offset, b1 & 0xFF,
							b2 & 0xFF);
					return;
				}

				if (b1 == -1) {
					System.out.println("Files are identical.");
					return;
				}

				offset++;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String original = "res/HotA.dat";
		String written = "res/output.dat";

		printFirstDifference(new File(original), new File(written));
		long crcOriginal = computeCRC(original);
		long crcWritten = computeCRC(written);

		if (crcOriginal == crcWritten) {
			System.out.println("Files are identical!");
		} else {
			System.out.println("Files differ!");
		}

		System.out.printf("Original CRC: %08X%nWritten CRC:  %08X%n", crcOriginal, crcWritten);
	}
}
