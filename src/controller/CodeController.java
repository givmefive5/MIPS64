package controller;

import java.util.List;

import Model.Instruction;
import gui.CodePanel;

public class CodeController {
	private static CodeController codeController;
	private CodePanel codePanel;

	private CodeController() {
		codePanel = CodePanel.getInstance();
	}

	public static CodeController getInstance() {
		if (codeController == null)
			codeController = new CodeController();
		return codeController;
	}

	public void setCodeValues(List<Instruction> instructions) {
		codePanel.resetCodeValues();
		codePanel.setCodeValues(instructions);
	}
}
