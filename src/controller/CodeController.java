package controller;

import org.apache.commons.lang3.StringUtils;

public class CodeController {
	private static String[][] code;

	public static String[][] getCodeValues() {
		if (code == null) {
			initCode();
		}
		return code;
	}

	private static void initCode() {
		code = new String[2048][4];

		int i = 0;
		String hex = "";
		while (!hex.equals("1FFC")) {
			int mem = i * 4;
			hex = Integer.toHexString(mem).toUpperCase();
			hex = StringUtils.leftPad(hex, 4, "0");
			code[i][0] = hex;
			code[i][1] = "0000000000000000";
			i++;
		}

	}
}
