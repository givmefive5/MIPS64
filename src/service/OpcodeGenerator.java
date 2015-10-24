package service;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;

public class OpcodeGenerator {

	public static List<Instruction> setBinaryOpcodes(List<Instruction> instructions) {
		for (int i = 0; i < instructions.size(); i++) {
			Instruction ins = instructions.get(i);
			String opcode = getBinaryOpcode(ins, i, instructions);
			ins.setOpcode(opcode);
			System.out.println(ins.getCommand());
			BigInteger b = new BigInteger(opcode, 2);
			String hexOpcode = b.toString(16).toUpperCase();
			hexOpcode = StringUtils.leftPad(hexOpcode, 8, "0");
			ins.setHexOpcode(hexOpcode);
		}
		return instructions;
	}

	private static String getBinaryOpcode(Instruction ins, int lineNumber, List<Instruction> instructions) {
		String opcode = "";
		String command = ins.getCommand();
		if (command.equals("DADDU") || command.equals("OR") || command.equals("SLT") || command.equals("DMULT")) {
			opcode += StringUtils.leftPad("", 6, "0");
			opcode += getBinaryOfRegister(ins.getRs());
			opcode += getBinaryOfRegister(ins.getRt());
			if (command.equals("DMULT"))
				opcode += StringUtils.leftPad("", 5, "0");
			else
				opcode += getBinaryOfRegister(ins.getRd());
			opcode += StringUtils.leftPad("", 5, "0");

			int funcVal = 0;
			if (command.equals("DADDU"))
				funcVal = 45;
			else if (command.equals("OR"))
				funcVal = 37;
			else if (command.equals("SLT"))
				funcVal = 42;
			else if (command.equals("DMULT"))
				funcVal = 28;

			opcode += StringUtils.leftPad(Integer.toBinaryString(funcVal), 6, "0");
		} else if (command.equals("LW") || command.equals("LWU") || command.equals("SW") || command.equals("L.S")
				|| command.equals("S.S")) {
			int opcodeVal = 0;
			if (command.equals("LW"))
				opcodeVal = 35;
			else if (command.equals("LWU"))
				opcodeVal = 39;
			else if (command.equals("SW"))
				opcodeVal = 43;
			else if (command.equals("L.S"))
				opcodeVal = 49;
			else if (command.equals("S.S"))
				opcodeVal = 57;

			opcode += StringUtils.leftPad(Integer.toBinaryString(opcodeVal), 6, "0");
			opcode += getBinaryOfRegister(ins.getRs());
			if (command.equals("LW") || command.equals("LWU") || command.equals("L.S"))
				opcode += getBinaryOfRegister(ins.getRd());
			else if (command.equals("SW") || command.equals("S.S"))
				opcode += getBinaryOfRegister(ins.getRt());
			opcode += StringUtils.leftPad(getBinaryFromHex(ins.getImm()), 16, "0");

		} else if (command.equals("DADDIU") || command.equals("ANDI")) {
			int opcodeVal = 0;
			if (command.equals("DADDIU"))
				opcodeVal = 25;
			else if (command.equals("ANDI"))
				opcodeVal = 12;

			opcode += StringUtils.leftPad(Integer.toBinaryString(opcodeVal), 6, "0");
			opcode += getBinaryOfRegister(ins.getRs());
			opcode += getBinaryOfRegister(ins.getRd());
			opcode += StringUtils.leftPad(getBinaryFromHex(ins.getImm()), 16, "0");
		} else if (command.equals("ADD.S") || command.equals("MUL.S")) {
			int opcodeVal = 17;
			opcode += StringUtils.leftPad(Integer.toBinaryString(opcodeVal), 6, "0");
			opcode += StringUtils.leftPad(Integer.toBinaryString(16), 5, "0");
			opcode += getBinaryOfRegister(ins.getRt());
			opcode += getBinaryOfRegister(ins.getRs());
			opcode += getBinaryOfRegister(ins.getRd());
			int func = 0;
			if (command.equals("ADD.S"))
				func = 0;
			else if (command.equals("MUL.S"))
				func = 2;
			opcode += StringUtils.leftPad(Integer.toBinaryString(func), 6, "0");
		} else if (command.equals("DSLL")) {
			opcode += StringUtils.leftPad("", 6, "0");
			opcode += StringUtils.leftPad("", 5, "0");
			opcode += getBinaryOfRegister(ins.getRs());
			opcode += getBinaryOfRegister(ins.getRd());
			opcode += StringUtils.leftPad(getBinaryFromHex(ins.getShift()), 5, "0");
			int opcodeVal = 56;
			opcode += StringUtils.leftPad(Integer.toBinaryString(opcodeVal), 6, "0");
		} else if (command.equals("J")) {
			opcode += StringUtils.leftPad(Integer.toBinaryString(2), 6, "0");
			int jumpLinkLineNumber = findLabel(instructions, ins.getJumpLink());
			opcode += StringUtils.leftPad(Integer.toBinaryString(jumpLinkLineNumber), 26, "0");
		} else if (command.equals("BEQ")) {
			opcode += StringUtils.leftPad(Integer.toBinaryString(4), 6, "0");
			opcode += getBinaryOfRegister(ins.getRs());
			opcode += getBinaryOfRegister(ins.getRt());
			int jumpLinkLineNumber = findLabel(instructions, ins.getJumpLink());
			int relativeOffset = jumpLinkLineNumber - (lineNumber + 1);
			opcode += StringUtils.leftPad(Integer.toBinaryString(relativeOffset), 16, "0");
		}

		return opcode;
	}

	private static int findLabel(List<Instruction> instructions, String jumpLink) {
		for (int i = 0; i < instructions.size(); i++) {
			Instruction ins = instructions.get(i);
			if (ins.getLabel() != null && ins.getLabel().equals(jumpLink))
				return i;
		}
		return -1;
	}

	private static String getBinaryFromHex(String hex) {
		return Integer.toBinaryString(Integer.parseInt(hex, 16));
	}

	private static String getBinaryOfRegister(String r) {
		String s = r.substring(1);
		String temp = Integer.toBinaryString(Integer.parseInt(s));
		return StringUtils.leftPad(temp, 5, "0");
	}
}
