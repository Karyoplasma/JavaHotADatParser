package core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

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

    public static void main(String[] args) throws IOException {
        String original = "res/HotA.dat";
        String written = "res/output.dat";

        long crcOriginal = computeCRC(original);
        long crcWritten  = computeCRC(written);

        if (crcOriginal == crcWritten) {
            System.out.println("Files are identical!");
        } else {
            System.out.println("Files differ!");
        }

        System.out.printf("Original CRC: %08X%nWritten CRC:  %08X%n", crcOriginal, crcWritten);
    }
}

