package core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class HDATParser {
	private Path hdatPath;
	private Charset charset;
	private ByteBuffer intBuffer, boolBuffer, scratchBuffer;

	public HDATParser(Path hdatPath, Charset charset) {
		this.hdatPath = hdatPath;
		this.charset = charset;
		this.intBuffer = ByteBuffer.allocate(4);
		this.intBuffer.order(ByteOrder.LITTLE_ENDIAN);
		this.boolBuffer = ByteBuffer.allocate(1);
		this.scratchBuffer = ByteBuffer.allocate(1024);
	}

	public List<HDATEntry> parseHDAT() {
		List<HDATEntry> ret = new ArrayList<HDATEntry>();
		try (FileChannel channel = FileChannel.open(this.hdatPath, StandardOpenOption.READ)) {
			String header = readString(channel, 4);
			header += readInt32(channel);
			if (!header.equals("HDAT2")) {
				System.out.println("This does not appear to be a valid HotA.dat file.");
				return ret;
			}
			int numberOfFiles = readInt32(channel);
			for (int i = 0; i < numberOfFiles; i++) {
				HDATEntry entry = new HDATEntry();
				entry.setName(readString(channel, readInt32(channel)));
				entry.setFolderName(readString(channel, readInt32(channel)));
				entry.setInt1(readInt32(channel));
				entry.setInt2(readInt32(channel));
				if (entry.getInt2() > 0) {
					entry.setData1(readString(channel, entry.getInt2()));
				} else {
					entry.setData1(readString(channel, readInt32(channel)));
				}
				entry.setData2(readString(channel, readInt32(channel)));
				entry.setData3(readString(channel, readInt32(channel)));
				entry.setData4(readString(channel, readInt32(channel)));
				entry.setData5(readString(channel, readInt32(channel)));
				entry.setData6(readString(channel, readInt32(channel)));
				entry.setData7(readString(channel, readInt32(channel)));
				entry.setData8(readString(channel, readInt32(channel)));
				if (entry.getInt2() > 0) {
					entry.setNewData(readString(channel, readInt32(channel)));
				}
				if (readBool(channel)) {
					entry.setExtraData(readExtraData(channel, readInt32(channel)));
				}
				entry.setExtraInts(readExtraInts(channel, readInt32(channel)));
				ret.add(entry);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		return ret;
	}

	private int[] readExtraInts(FileChannel channel, int length) throws IOException {
		int[] ret = new int[length];
		for (int i = 0; i < length; i++) {
			ret[i] = readInt32(channel);
		}
		return ret;
	}

	private byte[] readExtraData(FileChannel channel, int length) throws IOException {
		if (scratchBuffer.capacity() < length) {
			scratchBuffer = ByteBuffer.allocate(length);
		}

		scratchBuffer.clear();
		scratchBuffer.limit(length);

		int bytesRead = 0;
		while (bytesRead < length) {
			int n = channel.read(scratchBuffer);
			if (n == -1)
				System.out.println("Unexpected end of file");
			bytesRead += n;
		}
		scratchBuffer.flip();
		byte[] extraData = new byte[length];
		scratchBuffer.get(extraData);
		return extraData;
	}

	private String readString(FileChannel channel, int length) throws IOException {
		if (scratchBuffer.capacity() < length) {
			scratchBuffer = ByteBuffer.allocate(length);
		}

		scratchBuffer.clear();
		scratchBuffer.limit(length);

		int bytesRead = 0;
		while (bytesRead < length) {
			int n = channel.read(scratchBuffer);
			if (n == -1)
				System.out.println("Unexpected end of file");
			bytesRead += n;
		}

		scratchBuffer.flip();
		return charset.decode(scratchBuffer).toString();
	}

	private int readInt32(FileChannel channel) throws IOException {
		this.intBuffer.clear();
		channel.read(this.intBuffer);
		this.intBuffer.flip();
		return this.intBuffer.getInt();
	}

	private boolean readBool(FileChannel channel) throws IOException {
		this.boolBuffer.clear();
		channel.read(this.boolBuffer);
		this.boolBuffer.flip();
		return this.boolBuffer.get() != 0;
	}

	public void writeHDAT(List<HDATEntry> entries) {
		try (RandomAccessFile raf = new RandomAccessFile("res/output.dat", "rw");
				FileChannel channel = raf.getChannel()) {
			byte[] header = "HDAT".getBytes(this.charset);
			ByteBuffer headerBuffer = ByteBuffer.wrap(header);
			channel.write(headerBuffer);
			headerBuffer.clear();
			writeInt32(channel, 2);
			writeInt32(channel, entries.size());
			for (HDATEntry entry : entries) {
				writeString(channel, entry.getName());
				writeString(channel, entry.getFolderName());
				writeInt32(channel, entry.getInt1());
				if (entry.getInt2() == 0) {
					writeInt32(channel, entry.getInt2());
				}
				writeString(channel, entry.getData1());
				writeString(channel, entry.getData2());
				writeString(channel, entry.getData3());
				writeString(channel, entry.getData4());
				writeString(channel, entry.getData5());
				writeString(channel, entry.getData6());
				writeString(channel, entry.getData7());
				writeString(channel, entry.getData8());

				if (entry.getInt2() > 0) {
					writeString(channel, entry.getNewData());
				}

				if (entry.getExtraData() != null) {
					this.boolBuffer.clear();
					this.boolBuffer.put((byte) 1);
					this.boolBuffer.flip();
					channel.write(boolBuffer);
					writeExtraData(channel, entry.getExtraData());
				} else {
					this.boolBuffer.clear();
					this.boolBuffer.put((byte) 0);
					this.boolBuffer.flip();
					channel.write(boolBuffer);
				}

				writeExtraInts(channel, entry.getExtraInts());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void writeExtraInts(FileChannel channel, int[] extraInts) throws IOException {
		if (extraInts == null) {
			writeInt32(channel, 0);
			return;
		}
		writeInt32(channel, extraInts.length);
		for (int i : extraInts) {
			writeInt32(channel, i);
		}
	}

	private void writeExtraData(FileChannel channel, byte[] extraData) throws IOException {
		if (extraData == null) {
			writeInt32(channel, 0);
			return;
		}
		writeInt32(channel, extraData.length);
		ByteBuffer buffer = ByteBuffer.wrap(extraData);
		channel.write(buffer);
	}

	private void writeInt32(FileChannel channel, int integer) throws IOException {
		this.intBuffer.clear();
		this.intBuffer.putInt(integer);
		this.intBuffer.flip();
		channel.write(intBuffer);
	}
	
	private void writeString(FileChannel channel, String string) throws IOException {
	    byte[] bytes = string.getBytes(charset);
	    
	    writeInt32(channel, bytes.length);

	    if (scratchBuffer.capacity() < bytes.length) {
	        scratchBuffer = ByteBuffer.allocate(bytes.length);
	    }

	    scratchBuffer.clear();
	    scratchBuffer.put(bytes);
	    scratchBuffer.flip();
	    channel.write(scratchBuffer);
	}

	public static void main(String[] args) {
		HDATParser parser = new HDATParser(Paths.get("res/HotA.dat"), Charset.forName("windows-1251"));
		List<HDATEntry> entries = parser.parseHDAT();
		System.out.println("Entries: " + entries.size());
		parser.writeHDAT(entries);
	}
}
