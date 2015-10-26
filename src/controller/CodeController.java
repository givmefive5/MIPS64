package controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;
import gui.CodePanel;

public class CodeController {
	private static CodeController codeController;
	private String[][] codes;
	private CodePanel codePanel = CodePanel.getInstance();

	private CodeController() {
		initCode();
		codePanel.initTable(codes);
	}

	public static CodeController getInstance() {
		if (codeController == null)
			codeController = new CodeController();
		return codeController;
	}

	private void initCode() {
		codes = new String[2048][4];

		int i = 0;
		String hex = "";
		while (!hex.equals("1FFC")) {
			int mem = i * 4;
			hex = Integer.toHexString(mem).toUpperCase();
			hex = StringUtils.leftPad(hex, 4, "0");
			codes[i][0] = hex;
			codes[i][1] = "00000000";
			i++;
		}
	}

	public void setCodeValues(List<Instruction> instructions) {
		initCode();
		codePanel.resetCodeValues(codes);
		codePanel.setCodeValues(instructions);
	}
}
