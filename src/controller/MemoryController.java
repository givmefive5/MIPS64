package controller;

import org.apache.commons.lang3.StringUtils;

public class MemoryController {

	private static String[][] memory;

	public static String[][] getMemoryValues() {
		if (memory == null) {
			initMemory();
		}
		return memory;
	}

	private static void initMemory() {
		memory = new String[2048][2];

		int i = 0;
		String hex = "";
		while (!hex.equals("3FF8")) {
			int mem = i * 8;
			hex = Integer.toHexString(mem).toUpperCase();
			hex = StringUtils.leftPad(hex, 4, "0");
			memory[i][0] = hex;
			memory[i][1] = "0000000000000000";
			i++;

		}

	}
}
