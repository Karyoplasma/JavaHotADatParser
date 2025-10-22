package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Objects;

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

	public static HDATEntry fromText(String text) throws NumberFormatException, IOException {
		HDATEntry temp = new HDATEntry();
		try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
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
		} 

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

		return temp;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(extraData);
		result = prime * result + Arrays.hashCode(extraInts);
		result = prime * result + Objects.hash(data1, data2, data3, data4, data5, data6, data7, data8, folderName, int1,
				int2, name, newData);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof HDATEntry))
			return false;
		HDATEntry other = (HDATEntry) obj;
		return Objects.equals(data1, other.data1) && Objects.equals(data2, other.data2)
				&& Objects.equals(data3, other.data3) && Objects.equals(data4, other.data4)
				&& Objects.equals(data5, other.data5) && Objects.equals(data6, other.data6)
				&& Objects.equals(data7, other.data7) && Objects.equals(data8, other.data8)
				&& Arrays.equals(extraData, other.extraData) && Arrays.equals(extraInts, other.extraInts)
				&& Objects.equals(folderName, other.folderName) && int1 == other.int1 && int2 == other.int2
				&& Objects.equals(name, other.name) && Objects.equals(newData, other.newData);
	}
}
