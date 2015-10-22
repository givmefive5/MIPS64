package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import Model.Error;
import Model.Instruction;
import exceptions.MIPSCodeParsingException;
import gui.InputErrorDialog;
import service.MIPS64Parser;
import service.OpcodeGenerator;

public class FrameController {

	private static String input;
	public static CodeController codeController;

	public FrameController() {
		codeController = new CodeController();
	}

	public static void setInput(File f) throws IOException {
		input = new String(Files.readAllBytes(f.toPath()));
		handleInput();
	}

	public static void setInput(String text) {
		input = text;
		handleInput();
	}

	private static void handleInput() {
		try {
			List<Instruction> instructions = MIPS64Parser.parse(input);
			instructions = OpcodeGenerator.setBinaryOpcodes(instructions);
			for (Instruction ins : instructions) {
				System.out.println(ins.getLine() + " " + ins.getOpcode());
			}
			codeController.setCodeValues(instructions);
		} catch (MIPSCodeParsingException e) {
			List<Error> errors = e.getErrors();
			new InputErrorDialog(errors);
		}
	}

	// if (ins != null) {
	// System.out.println("command: " + ins.getCommand());
	// System.out.println("rd: " + ins.getRd());
	// System.out.println("rs: " + ins.getRs());
	// System.out.println("rt: " + ins.getRt());
	// System.out.println("imm: " + ins.getImm());
	// System.out.println("shift: " + ins.getShift());
	// System.out.println("jumpLink: " + ins.getJumpLink());
	// System.out.println("comment: " + ins.getComment());
	// System.out.println("label: " + ins.getLabel());
	// } else {
	// System.out.println("INS IS NULL: instruction: " + s);
	// }

}
