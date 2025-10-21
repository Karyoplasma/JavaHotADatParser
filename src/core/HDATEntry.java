package core;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;

public class HDATEntry {
	String name, folderName, data1, data2, data3, data4, data5, data6, data7, data8, newData;
	private int int1, int2;
	private byte[] extraData;
	private int[] extraInts;

	public HDATEntry() {

	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public void setData1(String data1) {
		this.data1 = data1;
	}

	public void setData2(String data2) {
		this.data2 = data2;
	}

	public void setData3(String data3) {
		this.data3 = data3;
	}

	public void setData4(String data4) {
		this.data4 = data4;
	}

	public void setData5(String data5) {
		this.data5 = data5;
	}

	public void setData6(String data6) {
		this.data6 = data6;
	}

	public void setData7(String data7) {
		this.data7 = data7;
	}

	public void setData8(String data8) {
		this.data8 = data8;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}

	public void setInt1(int int1) {
		this.int1 = int1;
	}

	public void setInt2(int int2) {
		this.int2 = int2;
	}

	public void setExtraData(byte[] extraData) {
		this.extraData = extraData;
	}

	public void setExtraInts(int[] extraInts) {
		this.extraInts = extraInts;
	}

	public int getInt2() {
		return this.int2;
	}

	public String getName() {
		return this.name;
	}

	public String getFolderName() {
		return folderName;
	}

	public String getData1() {
		return data1;
	}

	public String getData2() {
		return data2;
	}

	public String getData3() {
		return data3;
	}

	public String getData4() {
		return data4;
	}

	public String getData5() {
		return data5;
	}

	public String getData6() {
		return data6;
	}

	public String getData7() {
		return data7;
	}

	public String getData8() {
		return data8;
	}

	public String getNewData() {
		return newData;
	}

	public int getInt1() {
		return int1;
	}

	public byte[] getExtraData() {
		return extraData;
	}

	public int[] getExtraInts() {
		return extraInts;
	}
	
	@Deprecated
	public void exportToFile(Path exportPath, Charset charset) {
		try (PrintWriter writer = new PrintWriter(exportPath.resolve(name).toFile(), charset.toString())) {
			writer.println("name=" + this.name);
			writer.println("folderName=" + this.folderName);
			writer.println("int1=" + this.int1);
			writer.println("int2=" + this.int2);
			if (!this.data1.isEmpty()) {
				writer.println("[DATA1]");
				writer.println(this.data1);
			}
			if (!this.data2.isEmpty()) {
				writer.println("[DATA2]");
				writer.println(this.data2);
			}
			if (!this.data3.isEmpty()) {
				writer.println("[DATA3]");
				writer.println(this.data3);
			}
			if (!this.data4.isEmpty()) {
				writer.println("[DATA4]");
				writer.println(this.data4);
			}
			if (!this.data5.isEmpty()) {
				writer.println("[DATA5]");
				writer.println(this.data5);
			}
			if (!this.data6.isEmpty()) {
				writer.println("[DATA6]");
				writer.println(this.data6);
			}
			if (!this.data7.isEmpty()) {
				writer.println("[DATA7]");
				writer.println(this.data7);
			}
			if (!this.data8.isEmpty()) {
				writer.println("[DATA8]");
				writer.println(this.data8);
			}
			if (this.int2 > 0) {
				writer.println("[NEWDATA]");
				writer.println(this.newData);
			}
			if (this.extraData != null) {
				writer.println("[EXTRADATA]");
				String temp = Arrays.toString(this.extraData);
				writer.println(temp.substring(1, temp.length() - 1));
			}
			if (this.extraInts != null) {
				writer.println("[EXTRAINTS]");
				String temp = Arrays.toString(this.extraInts);
				writer.print(temp.substring(1, temp.length() - 1));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder.append("name=").append(this.name).append(newLine);
		builder.append("folderName=").append(this.folderName).append(newLine);
		builder.append("int1=").append(this.int1).append(newLine);
		builder.append("int2=").append(this.int2).append(newLine);
		if (!this.data1.isEmpty()) {
			builder.append("[DATA1]").append(newLine);
			builder.append(this.data1).append(newLine);
		}
		if (!this.data2.isEmpty()) {
			builder.append("[DATA2]").append(newLine);
			builder.append(this.data2).append(newLine);
		}
		if (!this.data3.isEmpty()) {
			builder.append("[DATA3]").append(newLine);
			builder.append(this.data3).append(newLine);
		}
		if (!this.data4.isEmpty()) {
			builder.append("[DATA4]").append(newLine);
			builder.append(this.data4).append(newLine);
		}
		if (!this.data5.isEmpty()) {
			builder.append("[DATA5]").append(newLine);
			builder.append(this.data5).append(newLine);
		}
		if (!this.data6.isEmpty()) {
			builder.append("[DATA6]").append(newLine);
			builder.append(this.data6).append(newLine);
		}
		if (!this.data7.isEmpty()) {
			builder.append("[DATA7]").append(newLine);
			builder.append(this.data7).append(newLine);
		}
		if (!this.data8.isEmpty()) {
			builder.append("[DATA8]").append(newLine);
			builder.append(this.data8).append(newLine);
		}
		if (this.int2 > 0) {
			builder.append("[NEWDATA]").append(newLine);
			builder.append(this.newData).append(newLine);
		}
		if (this.extraData != null) {
			builder.append("[EXTRADATA]").append(newLine);
			String temp = Arrays.toString(this.extraData);
			builder.append(temp.substring(1, temp.length() - 1)).append(newLine);
		}
		if (this.extraInts != null) {
			builder.append("[EXTRAINTS]").append(newLine);
			String temp = Arrays.toString(this.extraInts);
			builder.append(temp.substring(1, temp.length() - 1)).append(newLine);
		}
		return builder.toString();
	}

	public void test(HDATEntry other) {
		if (!other.data1.equals(this.data1)) {
			System.out.println("Data 1 differs:");
			System.out.println("orig : " + data1);
			System.out.println("other: " + other.data1);
		}
		if (!other.data2.equals(this.data2)) {
			System.out.println("Data 2 differs:");
			System.out.println("orig : " + data2);
			System.out.println("other: " + other.data2);
		}
		if (!other.data3.equals(this.data3)) {
			System.out.println("Data 3 differs:");
			System.out.println("orig : " + data3);
			System.out.println("other: " + other.data3);
		}
		if (!other.data4.equals(this.data4)) {
			System.out.println("Data 4 differs:");
			System.out.println("orig : " + data4);
			System.out.println("other: " + other.data4);
		}
		if (!other.data5.equals(this.data5)) {
			System.out.println("Data 5 differs:");
			System.out.println("orig : " + data5);
			System.out.println("other: " + other.data5);
		}
		if (!other.data6.equals(this.data6)) {
			System.out.println("Data 6 differs:");
			System.out.println("orig : " + data6);
			System.out.println("other: " + other.data6);
		}
		if (!other.data7.equals(this.data7)) {
			System.out.println("Data 7 differs:");
			System.out.println("orig : " + data7);
			System.out.println("other: " + other.data7);
		}
		if (!other.data8.equals(this.data8)) {
			System.out.println("Data 8 differs:");
			System.out.println("orig : " + data8);
			System.out.println("other: " + other.data8);
		}
		if (other.newData != null) {
			if (!other.newData.equals(this.newData)) {
				System.out.println("NewData differs:");
				System.out.println("orig : " + newData);
				System.out.println("other: " + other.newData);
			}
		}
	}
}
