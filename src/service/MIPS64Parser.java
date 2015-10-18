package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import Model.Error;
import Model.Instruction;
import exceptions.InvalidFormatException;
import exceptions.InvalidParameterException;
import exceptions.InvalidRegisterException;
import exceptions.MIPSCodeParsingException;
import exceptions.UnrecognizedCommandException;

public class MIPS64Parser {

	public static List<Instruction> parse(String text) throws MIPSCodeParsingException {
		String[] instructionsHandled = { "DADDU", "DMULT", "OR", "SLT", "BEQ", "LW", "LWU", "SW", "DSLL", "ANDI",
				"DADDIU", "J", "L.S", "S.S", "ADD.S", "MUL.S" };
		List<Instruction> instructions = new ArrayList<>();

		String[] lines = text.split("\\r?\\n");

		List<Error> errors = new ArrayList<>();
		for (int i = 0; i < lines.length; i++) {
			String s = lines[i];
			Scanner scanner = new Scanner(s);

			if (scanner.hasNext()) {
				String firstArg = scanner.next();
				String label = null;
				String command;
				String[] registers;

				if (firstArg.charAt(firstArg.length() - 1) == ':') {
					label = firstArg.substring(0, firstArg.length() - 1);
					command = scanner.next();
				} else if (Arrays.asList(instructionsHandled).contains(firstArg)) {
					command = firstArg;
				} else {
					errors.add(new Error(s, i, "Invalid Label"));
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
					instructions.add(ins);
				} catch (UnrecognizedCommandException | InvalidParameterException | InvalidFormatException e) {
					System.out.println(e.getMessage());
					errors.add(new Error(s, i, e.getMessage()));
				}
				scanner.close();
			}
		}
		if (errors.size() > 0) {
			throw new MIPSCodeParsingException(errors);
		}

		return instructions;

	}

	private static Instruction getInstruction(String command, String label, String comment, String[] registers)
			throws UnrecognizedCommandException, InvalidParameterException, InvalidFormatException {
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
				if (command.equals("DADDU") || command.equals("OR") || command.equals("SLT")) {
					areCorrectRegisterType('r', rd, rs, rt);
				} else
					areCorrectRegisterType('f', rd, rs, rt);

			} else if (command.equals("DMULT")) {
				rs = registers[0];
				rt = registers[1];
				areCorrectRegisterType('r', null, rs, rt);
			} else if (command.equals("BEQ")) {
				rs = registers[0];
				rt = registers[1];
				jumpLink = registers[2];
				areCorrectRegisterType('r', null, rs, rt);
			} else if (command.equals("LW") || command.equals("LWU") || command.equals("L.S")) {
				rd = registers[0];
				imm = registers[1].substring(0, registers[1].indexOf("("));
				rs = registers[1].substring(registers[1].indexOf("(") + 1, registers[1].indexOf(")"));
				if (command.equals("LW") || command.equals("LWU"))
					areCorrectRegisterType('r', rd, rs, null);
				else
					areCorrectRegisterType('f', rd, rs, null);
			} else if (command.equals("SW") || command.equals("S.S")) {
				rt = registers[0];
				imm = registers[1].substring(0, registers[1].indexOf("("));
				rs = registers[1].substring(registers[1].indexOf("(") + 1, registers[1].indexOf(")"));
				if (command.equals("SW"))
					areCorrectRegisterType('r', null, rs, rt);
				else
					areCorrectRegisterType('f', null, rs, rt);
			} else if (command.equals("DSLL")) {
				rd = registers[0];
				rs = registers[1];
				shift = registers[2];
				areCorrectRegisterType('r', rd, rs, null);
			} else if (command.equals("DADDIU") || command.equals("ANDI")) {
				rd = registers[0];
				rs = registers[1];
				imm = registers[2];
				areCorrectRegisterType('r', rd, rs, null);
			} else if (command.equals("J")) {
				jumpLink = registers[0];
			} else {
				throw new UnrecognizedCommandException();
			}
			Instruction ins = new Instruction(command, rd, rs, rt, imm, shift, label, jumpLink, comment);
			return ins;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidParameterException();
		} catch (InvalidRegisterException e) {
			String message = e.getMessage() + ": " + Arrays.toString(e.getInvalidRegisters());
			throw new InvalidFormatException(message);
		}
	}

	// Checks if all registers are all Rs or all Fs.
	private static boolean areCorrectRegisterType(char expectedType, String rd, String rs, String rt)
			throws InvalidRegisterException {

		List<String> invalidRegisters = new ArrayList<>();
		String[] registers = { rd, rs, rt };
		for (String r : registers) {
			if (r != null) {
				try {
					if (!(r.length() >= 2 && r.length() <= 3 && r.toLowerCase().charAt(0) == expectedType
							&& Integer.parseInt(r.substring(1)) >= 0 && Integer.parseInt(r.substring(1)) < 32)) {
						invalidRegisters.add(r);
					}
				} catch (Exception e) {
					invalidRegisters.add(r);
				}

			}
		}

		if (invalidRegisters.size() > 0) {
			throw new InvalidRegisterException(invalidRegisters.toArray(new String[invalidRegisters.size()]));
		}

		return true;
	}

}
