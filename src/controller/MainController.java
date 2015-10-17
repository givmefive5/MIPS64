package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.JFrame;

import Model.Instruction;
import gui.InputUIHandler;
import gui.MainFrame;
import service.MIPS64Parser;
import service.OpcodeGenerator;

public class MainController {

	private static String input;

	public static void main(String[] args) {
		JFrame frame = MainFrame.getInstance();
		frame.setVisible(true);
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
			System.out.println(instructions.size());
			String s = OpcodeGenerator.getBinaryOpcode(instructions.get(0), 0, instructions);
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
			InputUIHandler.showInputError();
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
