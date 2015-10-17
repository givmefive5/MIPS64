package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Model.Command;
import Model.Instruction;
import exceptions.InvalidParameterException;
import exceptions.UnrecognizedCommandException;

public class MIPS64Parser {

	public static List<Instruction> parse(String text) {
		String[] instructionsHandled = { "DADDU", "DMULT", "OR", "SLT", "BEQ", "LW", "LWU", "SW", "DSLL", "ANDI",
				"DADDIU", "J", "L.S", "S.S", "ADD.S", "MUL.S" };
		List<Instruction> instructions = new ArrayList<>();

		String[] lines = text.split("\\r?\\n");

		for (String s : lines) {
			Scanner scanner = new Scanner(s);

			if (scanner.hasNext()) {
				String firstArg = scanner.next();
				String label = null;
				String command;
				String[] registers;

				if (firstArg.contains(":")) {
					label = firstArg;
					command = scanner.next();
				} else if (Arrays.asList(instructionsHandled).contains(firstArg)) {
					command = firstArg;
				} else {
					String message = "Invalid Label";
					System.out.println(message);
					// Add to error list
					continue;
				}
				// Splitting comment
				String[] temp = scanner.nextLine().split(";");

				String comment = null;
				if (temp.length > 1) {
					comment = temp[1];
				}
				registers = temp[0].replaceAll("\\s+", "").split(",");

				Instruction ins = null;
				try {
					ins = getInstruction(command, label, comment, registers);
				} catch (UnrecognizedCommandException | InvalidParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				instructions.add(ins);

				scanner.close();
			}
		}

		return instructions;

	}

	private static Instruction getInstruction(String command, String label, String comment, String[] registers)
			throws UnrecognizedCommandException, InvalidParameterException {
		String rd = null;
		String rs = null;
		String rt = null;
		String imm = null;
		String shift = null;
		String jumpLink = null;
		try {
			if (command.equals("DADDU") || command.equals("OR") || command.equals("SLT") || command.equals("ADD.S")
					|| command.equals("MUL.S")) {
				rd = registers[0];
				rs = registers[1];
				rt = registers[2];
			} else if (command.equals("DMULT")) {
				rs = registers[0];
				rt = registers[1];
			} else if (command.equals("BEQ")) {
				rs = registers[0];
				rt = registers[1];
				jumpLink = registers[2];
			} else if (command.equals("LW") || command.equals("LWU") || command.equals("L.S")) {
				rd = registers[0];
				imm = registers[1].substring(0, registers[1].indexOf("("));
				rs = registers[1].substring(registers[1].indexOf("(") + 1, registers[1].indexOf(")"));
			} else if (command.equals("SW") || command.equals("S.S")) {
				rt = registers[0];
				imm = registers[1].substring(0, registers[1].indexOf("("));
				rs = registers[1].substring(registers[1].indexOf("(") + 1, registers[1].indexOf(")"));
			} else if (command.equals("DSLL")) {
				rd = registers[0];
				rs = registers[1];
				shift = registers[2];
			} else if (command.equals("DADDIU") || command.equals("ANDI")) {
				rd = registers[0];
				rs = registers[1];
				imm = registers[2];
			} else if (command.equals("J")) {
				jumpLink = registers[0];
			} else {
				System.out.println(command);
				throw new UnrecognizedCommandException();
			}
			Instruction ins = new Instruction(command, rd, rs, rt, imm, shift, label, jumpLink, comment);
			return ins;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidParameterException();
		}
	}

	public static void printCommands(ArrayList<Command> com) {
		for (Command command : com) {
			System.out.print(command.getFuncName() + " " + command.getInstruction() + " ");
			for (String s : command.getRegisters())
				System.out.print(s + " ");
			System.out.println();
		}
	}

}
