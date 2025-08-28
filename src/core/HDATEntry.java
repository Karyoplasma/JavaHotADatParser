package core;

import java.util.Arrays;

public class HDATEntry {
	private String name, folderName, data1, data2, data3, data4, data5, data6, data7, data8, newData;
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

	@Override
	public String toString() {
		String newLine = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder.append("Name: ").append(this.name).append(newLine);
		builder.append("Folder name: ").append(this.folderName).append(newLine);
		builder.append("Int1: ").append(this.int1).append(newLine);
		builder.append("Int2: ").append(this.int2).append(newLine);
		builder.append(newLine);
		if (!this.data1.isEmpty()) {
			builder.append("data1:").append(newLine);
			builder.append(this.data1).append(newLine);
		}
		if (!this.data2.isEmpty()) {
			builder.append("data2:").append(newLine);
			builder.append(this.data2).append(newLine);
		}
		if (!this.data3.isEmpty()) {
			builder.append("data3:").append(newLine);
			builder.append(this.data3).append(newLine);
		}
		if (!this.data4.isEmpty()) {
			builder.append("data4:").append(newLine);
			builder.append(this.data4).append(newLine);
		}
		if (!this.data5.isEmpty()) {
			builder.append("data5:").append(newLine);
			builder.append(this.data5).append(newLine);
		}
		if (!this.data6.isEmpty()) {
			builder.append("data6:").append(newLine);
			builder.append(this.data6).append(newLine);
		}
		if (!this.data7.isEmpty()) {
			builder.append("data7:").append(newLine);
			builder.append(this.data7).append(newLine);
		}
		if (!this.data8.isEmpty()) {
			builder.append("data8:").append(newLine);
			builder.append(this.data8).append(newLine);
		}
		if (this.int2 > 0 ) {
			builder.append("newData:").append(newLine);
			builder.append(this.newData).append(newLine);
		}
		if (this.extraData != null) {
			builder.append("extraData:").append(newLine);
			builder.append(Arrays.toString(this.extraData)).append(newLine);
		}
		if (this.extraInts != null) {
			builder.append("extraInts:").append(newLine);
			builder.append(Arrays.toString(this.extraInts)).append(newLine);
		}
		return builder.toString();
	}
}
