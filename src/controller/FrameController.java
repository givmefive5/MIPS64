package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.JOptionPane;

import Model.Error;
import Model.Instruction;
import exceptions.MIPSCodeParsingException;
import exceptions.NoInputFoundException;
import gui.ConsoleInputBox;
import gui.InputErrorDialog;
import gui.MainFrame;
import service.MIPS64Parser;
import service.OpcodeGenerator;

public class FrameController {

	private static FrameController frameController;
	private static String input;
	private static MenuBarController menuBarController;
	private static CodeController codeController;
	private static MemoryController memoryController;
	private static RegistersController registersController;
	private static PipelineMapController pipelineMapController;

	private List<Instruction> instructions;

	private FrameController() {
	}

	public static FrameController getInstance() {
		if (frameController == null)
			initFrameController();
		return frameController;
	}

	private static void initFrameController() {
		frameController = new FrameController();
		menuBarController = MenuBarController.getInstance();
		codeController = CodeController.getInstance();
		memoryController = MemoryController.getInstance();
		registersController = RegistersController.getInstance();
		pipelineMapController = PipelineMapController.getInstance();
	}

	public void setInput(File f) throws IOException {
		input = new String(Files.readAllBytes(f.toPath()));
		handleInput("FILE");
	}

	public void setInput(String text) {
		input = text;
		handleInput("TEXT");
	}

	public List<Instruction> getInstructions() {
		return instructions;
	}

	private void handleInput(String inputType) {
		try {
			instructions = MIPS64Parser.parse(input);
			if (instructions.size() >= 1) {
				instructions = OpcodeGenerator.setBinaryOpcodes(instructions);
				codeController.setCodeValues(instructions);
				menuBarController.setExecuteMenuVisible(true);
				registersController.resetValues();
				memoryController.resetValues();
				pipelineMapController.resetValues();
				pipelineMapController.setCodeValues(instructions);
			} else {
				throw new NoInputFoundException("No lines found in new input");
			}

		} catch (MIPSCodeParsingException e) {
			List<Error> errors = e.getErrors();
			new InputErrorDialog(errors);

			if (inputType.equals("TEXT")) {
				ConsoleInputBox inputBox = new ConsoleInputBox();
				inputBox.showInputBox(input);
			}
		} catch (NoInputFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "Exception: No input found in inputted text.",
					"Error", JOptionPane.ERROR_MESSAGE);

			if (inputType.equals("TEXT")) {
				ConsoleInputBox inputBox = new ConsoleInputBox();
				inputBox.showInputBox(input);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(MainFrame.getInstance(),
					"Unknown Exception: Probably caused by unexpected file format.", "Error",
					JOptionPane.ERROR_MESSAGE);

			if (inputType.equals("TEXT")) {
				ConsoleInputBox inputBox = new ConsoleInputBox();
				inputBox.showInputBox(input);
			}
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
