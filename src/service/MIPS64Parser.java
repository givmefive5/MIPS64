package service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import Model.Error;
import Model.Instruction;
import exceptions.InvalidFormatException;
import exceptions.InvalidImmediateException;
import exceptions.InvalidParameterException;
import exceptions.InvalidRegisterException;
import exceptions.LabelNotFoundException;
import exceptions.MIPSCodeParsingException;
import exceptions.UnrecognizedCommandException;

public class MIPS64Parser {

	// public static List<Instruction> parse2(String text) throws
	// MIPSCodeParsingException {
	// String[] instructionsHandled = { "DADDU", "DMULT", "OR", "SLT", "BEQ",
	// "LW", "LWU", "SW", "DSLL", "ANDI",
	// "DADDIU", "J", "L.S", "S.S", "ADD.S", "MUL.S" };
	// List<Instruction> instructions = new ArrayList<>();
	// }

	@SuppressWarnings("resource")
	public static List<Instruction> parse(String text) throws MIPSCodeParsingException {
		String[] instructionsHandled = { "DADDU", "DMULT", "OR", "SLT", "BEQ", "LW", "LWU", "SW", "DSLL", "ANDI",
				"DADDIU", "J", "L.S", "S.S", "ADD.S", "MUL.S" };
		List<Instruction> instructions = new ArrayList<>();

		text = text.replaceAll("(?m)^[ \t]*\r?\n", "");
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
					if (scanner.hasNext())
						command = scanner.next();
					else {
						errors.add(new Error(s, i, "No instruction found!"));
						continue;
					}
				} else if (Arrays.asList(instructionsHandled).contains(firstArg.toUpperCase())) {
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
					if (registers.length > 3) {
						throw new InvalidParameterException();
					}
					ins = getInstruction(i, s, command, label, comment, registers);
					instructions.add(ins);
				} catch (UnrecognizedCommandException | InvalidParameterException | InvalidFormatException
						| InvalidImmediateException e) {
					errors.add(new Error(s, i, e.getMessage()));
				}
				scanner.close();
			}
		}

		for (Instruction ins : instructions) {
			try {
				validateJumpLink(ins, instructions);
				validateImmediate(ins);
			} catch (LabelNotFoundException e) {
				errors.add(new Error(e.getLine(), e.getLineNumber(), e.getMessage()));
			} catch (InvalidImmediateException e) {
				errors.add(new Error(e.getLine(), e.getLineNumber(), e.getMessage()));
			}

		}
		if (errors.size() > 0) {
			errors = sortErrorsByLineNumber(errors);
			throw new MIPSCodeParsingException(errors);
		}

		return instructions;

	}

	private static List<Error> sortErrorsByLineNumber(List<Error> errors) {
		Collections.sort(errors, new Comparator<Error>() {
			@Override
			public int compare(Error o1, Error o2) {
				if (o1.getLineNumber() == o2.getLineNumber())
					return 0;
				return o1.getLineNumber() < o2.getLineNumber() ? -1 : 1;
			}
		});
		return errors;
	}

	private static void validateImmediate(Instruction ins) throws InvalidImmediateException {
		String imm = ins.getImm();
		try {
			if (imm != null && imm.length() <= 4)
				Integer.parseInt(imm, 16);
			else if (imm != null)
				throw new InvalidImmediateException(imm, ins.getLine(), ins.getLineNumber());
		} catch (Exception e) {
			throw new InvalidImmediateException(imm, ins.getLine(), ins.getLineNumber());
		}
	}

	private static void validateJumpLink(Instruction ins, List<Instruction> instructions)
			throws LabelNotFoundException {
		if (ins.getJumpLink() != null) {
			boolean isFound = false;
			for (Instruction s : instructions) {
				if (s.getLabel() != null && ins.getJumpLink().equals(s.getLabel())) {
					isFound = true;
					break;
				}
			}
			if (isFound == false)
				throw new LabelNotFoundException(ins.getJumpLink(), ins.getLine(), ins.getLineNumber());
		}

	}

	private static Instruction getInstruction(int lineNumber, String line, String command, String label, String comment,
			String[] registers) throws UnrecognizedCommandException, InvalidParameterException, InvalidFormatException,
					InvalidImmediateException {
		String rd = null;
		String rs = null;
		String rt = null;
		String imm = null;
		String shift = null;
		String jumpLink = null;

		command = command.toUpperCase();
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
				else if (command.equals("L.S"))
					areCorrectRegisterType('f', 'r', rd, rs, null);
			} else if (command.equals("SW") || command.equals("S.S")) {
				rt = registers[0];
				imm = registers[1].substring(0, registers[1].indexOf("("));
				rs = registers[1].substring(registers[1].indexOf("(") + 1, registers[1].indexOf(")"));
				if (command.equals("SW"))
					areCorrectRegisterType('r', null, rs, rt);
				else if (command.equals("S.S"))
					areCorrectRegisterType('f', 'r', null, rs, rt);
			} else if (command.equals("DSLL")) {
				rd = registers[0];
				rs = registers[1];
				if (registers[2].startsWith("#"))
					shift = registers[2].substring(1);
				else
					throw new InvalidImmediateException(shift, line, lineNumber);
				areCorrectRegisterType('r', rd, rs, null);
			} else if (command.equals("DADDIU") || command.equals("ANDI")) {
				rd = registers[0];
				rs = registers[1];
				if (registers[2].startsWith("#"))
					imm = registers[2].substring(1);
				else
					throw new InvalidImmediateException(imm, line, lineNumber);
				areCorrectRegisterType('r', rd, rs, null);
			} else if (command.equals("J")) {
				jumpLink = registers[0];
			} else {
				throw new UnrecognizedCommandException();
			}
			Instruction ins = new Instruction(lineNumber, line, command, rd, rs, rt, imm, shift, label, jumpLink,
					comment);
			return ins;
		} catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
			throw new InvalidParameterException();
		} catch (InvalidRegisterException e) {
			String message = e.getMessage() + ": " + Arrays.toString(e.getInvalidRegisters());
			throw new InvalidFormatException(message);
		}
	}

	private static boolean areCorrectRegisterType(char expectedType1, char expectedType2, String rd, String rs,
			String rt) {
		boolean areCorrect = true;
		try {
			if (!(rs.length() >= 2 && rs.length() <= 3 && rs.toLowerCase().charAt(0) == expectedType2
					&& Integer.parseInt(rs.substring(1)) >= 0 && Integer.parseInt(rs.substring(1)) < 32)) {
				areCorrect = false;
			}
			if (rd != null) {
				if (!(rd.length() >= 2 && rd.length() <= 3 && rd.toLowerCase().charAt(0) == expectedType1
						&& Integer.parseInt(rd.substring(1)) >= 0 && Integer.parseInt(rd.substring(1)) < 32)) {
					areCorrect = false;
				}
			} else {// rt
				if (!(rt.length() >= 2 && rt.length() <= 3 && rt.toLowerCase().charAt(0) == expectedType1
						&& Integer.parseInt(rt.substring(1)) >= 0 && Integer.parseInt(rt.substring(1)) < 32)) {
					areCorrect = false;
				}
			}

		} catch (Exception e) {
			areCorrect = false;
		}

		return areCorrect;
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
