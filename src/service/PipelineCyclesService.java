package service;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;
import Model.InternalRegister;
import controller.MemoryController;
import controller.PipelineMapController;
import controller.RegistersController;

public class PipelineCyclesService {

	InternalRegister ir;
	List<Instruction> instructions;
	int ifLineNumber = 0;
	int idLineNumber = -1;
	int exLineNumber = -1;
	int memLineNumber = -1;
	int wbLineNumber = -1;
	int cycleNumber = 1;
	int ifFinished = -1;
	int idFinished = -1;
	int exFinished = -1;
	int memFinished = -1;
	int wbFinished = -1;
	int cycleNumberOfLastWBFinish = -1;
	boolean toStop = false;
	boolean branched = false;

	public PipelineCyclesService(InternalRegister ir) {
		this.ir = ir;
		// TODO: Using the line numbers to check whether or not an instruction
		// needs to stall or not.
	}

	public void singleCycleRun() {
		if (toStop == false) {
			System.out.println("Cycle: " + cycleNumber);
			callWB();
			callMEM();
			callEX();
			callID();
			callIF();
			cycleNumber++;

		} else {
			System.out.println("Stopped at Cycle " + cycleNumber);
		}

	}

	private boolean shouldStall(String functionType, int lineNumber) {
		if (lineNumber > 0 && lineNumber < instructions.size()) {
			Instruction curr = instructions.get(lineNumber);
			String rs = curr.getRs().toLowerCase();
			String rt = curr.getRt().toLowerCase();

			int i = lineNumber - 1;
			while (i >= lineNumber - 3 && i >= 0) {
				Instruction ins = instructions.get(i);

				if ((rs != null && ins.getRd() != null && rs.equals(ins.getRd().toLowerCase())
						&& (!rs.equals("r0") && !rs.equals("f0")))
						|| (rt != null && ins.getRd() != null && rt.equals(ins.getRd().toLowerCase())
								&& (!rt.equals("r0") && !rt.equals("f0")))) {

					if (wbFinished < i) {
						return true;
					} else if (wbFinished == i && cycleNumberOfLastWBFinish == cycleNumber)
						return true;
				}

				i--;
			}
		}
		return false;
	}

	private void callIF() {
		if (ifLineNumber < instructions.size() && (ifLineNumber - 1 == idFinished || branched)) {
			Instruction ins = instructions.get(ifLineNumber);

			PipelineMapController.setMapValue("IF", ifLineNumber, cycleNumber);
			ifFinished = ifLineNumber;

			String opcode = ir.getIFIDIR();
			String binary = null;
			if (opcode != null) {
				binary = BinaryHexConverter.convertHexToBinary(opcode);
			}
			System.out.println(binary);
			if (binary != null && binary.substring(0, 6).equals("000100")
					&& isEqualRegisters(binary.substring(6, 11), binary.substring(11, 16))) { // BEQ
				System.out.println("True");
				int npc;
				if (ir.getIFIDNPC() != null) {
					npc = Integer.parseInt(ir.getIFIDNPC(), 16);
				} else {
					npc = 0;
				}
				int imm = Integer.parseInt(binary.substring(16), 2) * 4;
				System.out.println("NPC " + npc + " IMM " + imm);
				String hex = StringUtils.leftPad(Integer.toHexString(npc + imm), 16, "0").toUpperCase();
				ir.setIFIDNPC(hex);
				ir.setPC(hex);
				idLineNumber = ifLineNumber;
				ifLineNumber = Integer.parseInt(hex, 16) / 4;
				branched = true;
				System.out.println("LineNumber + " + ifLineNumber);
			} else if (binary != null && binary.substring(0, 6).equals("000010")) { // J
				String address = binary.substring(8) + "00";
				String hex = BinaryHexConverter.convertBinaryToHex(address, 16).toUpperCase();
				ir.setIFIDNPC(hex);
				ir.setPC(hex);
				idLineNumber = ifLineNumber;
				ifLineNumber = Integer.parseInt(hex, 16) / 4;
				branched = true;
			} else {
				String npc = StringUtils.leftPad(Integer.toHexString((ins.getLineNumber() + 1) * 4), 16, "0")
						.toUpperCase();
				System.out.println("NPC " + npc);
				ir.setIFIDNPC(npc);
				ir.setPC(npc);
				idLineNumber = ifLineNumber;
				ifLineNumber++;
				branched = false;
			}
			ir.setIFIDIR(ins.getHexOpcode());
		}

	}

	private boolean isEqualRegisters(String regBin1, String regBin2) { // for
																		// BEQ
		if (regBin1 == null || regBin2 == null)
			return false;
		else {
			int index1 = Integer.parseInt(regBin1, 2);
			int index2 = Integer.parseInt(regBin2, 2);
			System.out.println("Index 1 " + index1 + " Index 2 " + index2);
			String val1 = RegistersController.getInstance().getValue(index1, 1);
			String val2 = RegistersController.getInstance().getValue(index2, 1);
			if (val1.equals(val2))
				return true;
			else
				return false;
		}

	}

	private void callID() {
		if (!shouldStall("ID", idLineNumber) && idLineNumber == ifFinished && idLineNumber < instructions.size()
				&& idLineNumber != idFinished && idLineNumber - 1 == exFinished) {
			Instruction ins = instructions.get(idLineNumber);
			String command = ins.getCommand().toUpperCase();
			BigInteger integer = new BigInteger(ins.getHexOpcode(), 16);
			String binary = StringUtils.leftPad(integer.toString(2), 32, "0");

			String imm, padChar;
			if (command.equals("DSLL")) {
				imm = binary.substring(21, 26);
				padChar = "0";
			} else {
				imm = binary.substring(16, 32);
				padChar = imm.substring(0, 1);
			}
			imm = StringUtils.leftPad(imm, 64, padChar);
			imm = BinaryHexConverter.convertBinaryToHex(imm, 16);

			int regA = Integer.parseInt(binary.substring(6, 11), 2);
			int regB = Integer.parseInt(binary.substring(11, 16), 2);

			String a, b;

			if (command.equals("ADD.S") || command.equals("MUL.S")) {
				regA = Integer.parseInt(binary.substring(16, 21), 2);
				regB = Integer.parseInt(binary.substring(11, 16), 2);
				a = RegistersController.getInstance().getValue(regA, 3);
				b = RegistersController.getInstance().getValue(regB, 3);
				ir.setIDEXA(a);
				ir.setIDEXB(b);
			} else if (command.equals("L.S") || command.equals("S.S")) {
				a = RegistersController.getInstance().getValue(regA, 1);
				b = RegistersController.getInstance().getValue(regB, 3);
				ir.setIDEXA(StringUtils.leftPad(a, 16, "0"));
				ir.setIDEXB(b);
			} else if (command.equals("DSLL")) {
				regA = Integer.parseInt(binary.substring(11, 16), 2);
				regB = Integer.parseInt(binary.substring(16, 21), 2);
				a = RegistersController.getInstance().getValue(regA, 1);
				b = RegistersController.getInstance().getValue(regB, 1);
				ir.setIDEXA(StringUtils.leftPad(a, 16, "0"));
				ir.setIDEXB(StringUtils.leftPad(b, 16, "0"));
			} else {
				a = RegistersController.getInstance().getValue(regA, 1);
				b = RegistersController.getInstance().getValue(regB, 1);
				ir.setIDEXA(StringUtils.leftPad(a, 16, "0"));
				ir.setIDEXB(StringUtils.leftPad(b, 16, "0"));
			}
			ir.setIDEXIR(ins.getHexOpcode());
			ir.setIDEXIMM(imm.toUpperCase());
			ir.setIDEXNPC(ir.getIFIDNPC());

			PipelineMapController.setMapValue("ID", idLineNumber, cycleNumber);
			exLineNumber = idLineNumber;
			idFinished = idLineNumber;
			idLineNumber++;
		}
	}

	private void callEX() {
		if (!shouldStall("ID", exLineNumber) && exLineNumber < instructions.size() && exLineNumber == idFinished
				&& exLineNumber != exFinished && exLineNumber - 1 == memFinished) {
			Instruction ins = instructions.get(exLineNumber);

			String command = ins.getCommand().toUpperCase();
			String aluOutput = "";
			String cond = "0";

			BigInteger imm = new BigInteger(ir.getIDEXIMM(), 16);

			if (command.equals("LW") || command.equals("LWU") || command.equals("SW") || command.equals("L.S")
					|| command.equals("S.S")) {
				BigInteger a = new BigInteger(ir.getIDEXA(), 16);
				aluOutput = a.add(imm).toString(16).toUpperCase();
				aluOutput = StringUtils.leftPad(aluOutput, 16, "0");
			} else if (command.equals("MUL.S") || command.equals("ADD.S")) {
				Float a = Float.parseFloat(ir.getIDEXA());
				Float b = Float.parseFloat(ir.getIDEXB());
				if (command.equals("MUL.S")) // MAY BE CHANGED requires 6 EX
												// cycles TODO
					aluOutput = Float.toString(a * b);
				else if (command.equals("ADD.S")) // TODO requires 4 EX cycles
					aluOutput = Float.toString(a + b);
			} else if (command.equals("DADDU") || command.equals("DMULT") || command.equals("OR")
					|| command.equals("SLT")) {
				BigInteger a = new BigInteger(ir.getIDEXA(), 16);
				BigInteger b = new BigInteger(ir.getIDEXB(), 16);
				if (command.equals("DADDU")) {
					aluOutput = a.add(b).toString(16).toUpperCase();
				} else if (command.equals("DMULT")) {
					aluOutput = a.multiply(b).toString(16).toUpperCase();
				} else if (command.equals("OR")) {
					aluOutput = a.or(b).toString(16).toUpperCase();
				} else if (command.equals("SLT")) {
					if (a.compareTo(b) == -1)
						aluOutput = StringUtils.leftPad("1", 16, "0");
					else
						aluOutput = StringUtils.leftPad("0", 16, "0");
				}
				aluOutput = StringUtils.leftPad(aluOutput, 16, "0");
			} else if (command.equals("DSLL") || command.equals("ANDI") || command.equals("DADDIU")) {
				if (command.equals("DSLL")) {
					int a = (int) Long.parseLong(ir.getIDEXA(), 16);
					int immediate = Integer.parseInt(ir.getIDEXIMM(), 16);
					int shifted = a << immediate;
					aluOutput = Integer.toHexString(shifted);
				} else if (command.equals("ANDI")) {
					BigInteger a = new BigInteger(ir.getIDEXA(), 16);
					aluOutput = a.or(imm).toString(16).toUpperCase();
				} else if (command.equals("DADDIU")) {
					BigInteger a = new BigInteger(ir.getIDEXA(), 16);
					aluOutput = a.add(imm).toString(16).toUpperCase();
				}
				aluOutput = StringUtils.leftPad(aluOutput, 16, "0");
			}

			ir.setEXMEMALUOutput(aluOutput);
			ir.setEXMEMCond(cond);
			ir.setEXMEMIR(ir.getIDEXIR());
			ir.setEXMEMB(ir.getIDEXB());

			PipelineMapController.setMapValue("EX", exLineNumber, cycleNumber);
			memLineNumber = exLineNumber;
			exFinished = exLineNumber;
			exLineNumber++;
		}

	}

	private void callMEM() {
		if (!shouldStall("ID", memLineNumber) && memLineNumber < instructions.size() && memLineNumber == exFinished
				&& memLineNumber != memFinished && memLineNumber - 1 == wbFinished) {
			Instruction ins = instructions.get(memLineNumber);
			ir.setMEMWBIR(ir.getEXMEMIR());
			ir.setMEMWBALUOutput(ir.getEXMEMALUOutput());
			if (ins.getCommand().equals("LW") || ins.getCommand().equals("LWU")) {
				String memHex = MemoryController.getHexWordFromMemory(ir.getEXMEMALUOutput());
				String memValBin = BinaryHexConverter.convertHexToBinary(memHex);
				String padChar = "0";
				if (ins.getCommand().equals("LW"))
					padChar = memValBin.substring(0, 1);
				memValBin = StringUtils.leftPad(memValBin, 64, padChar);
				String hex = BinaryHexConverter.convertBinaryToHex(memValBin, 16).toUpperCase();
				ir.setMEMWBLMD(hex);
			} else if (ins.getCommand().equals("L.S")) {
				String memoryValueHex = MemoryController.getHexWordFromMemory(ir.getEXMEMALUOutput());
				Float floatNum = FloatingPointConverter.convertHexToFloat(memoryValueHex);
				ir.setMEMWBLMD(floatNum.toString());
			} else if (ins.getCommand().equals("SW")) {
				String word = ir.getEXMEMB().substring(8, 16);
				MemoryController.storeWordToMemory(word, ir.getEXMEMALUOutput());
			} else if (ins.getCommand().equals("S.S")) {
				String floatString = ir.getEXMEMB();
				String hex = FloatingPointConverter.convertFloatToHex(Float.parseFloat(floatString));
				MemoryController.storeWordToMemory(hex, ir.getEXMEMALUOutput());
			}

			PipelineMapController.setMapValue("MEM", memLineNumber, cycleNumber);
			wbLineNumber = memLineNumber;
			memFinished = memLineNumber;
			memLineNumber++;
		}
	}

	private void callWB() {
		// TODO Auto-generated method stub
		if (!shouldStall("ID", wbLineNumber) && wbLineNumber < instructions.size() && wbLineNumber == memFinished
				&& wbLineNumber != wbFinished) {
			Instruction ins = instructions.get(wbLineNumber);

			if (ins.getCommand().equals("LW") || ins.getCommand().equals("LWU")) {
				int registerIndex = Integer.parseInt(ins.getRd().substring(1));
				RegistersController.setValue(ir.getMEMWBLMD(), registerIndex, 1);
				ir.setRn("R" + registerIndex + " = " + ir.getMEMWBLMD());
			} else if (ins.getCommand().equals("L.S")) {
				int registerIndex = Integer.parseInt(ins.getRd().substring(1));
				RegistersController.setValue(ir.getMEMWBLMD(), registerIndex, 3);
				ir.setRn("F" + registerIndex + " = " + ir.getMEMWBLMD());
			} else if (ins.getCommand().equals("ADD.S") || ins.getCommand().equals("DADDU")
					|| ins.getCommand().equals("OR") || ins.getCommand().equals("SLT")
					|| ins.getCommand().equals("DSLL") || ins.getCommand().equals("DADDIU")
					|| ins.getCommand().equals("ANDI")) {
				BigInteger integer = new BigInteger(ins.getHexOpcode(), 16);
				String binary = StringUtils.leftPad(integer.toString(2), 32, "0");
				int rd;
				if (ins.getCommand().equals("ADD.S")) {
					rd = Integer.parseInt(binary.substring(21, 26), 2);
					RegistersController.setValue(ir.getMEMWBALUOutput(), rd, 3);
					ir.setRn("F" + rd + " = " + ir.getMEMWBALUOutput());
				} else if (ins.getCommand().equals("DADDIU") || ins.getCommand().equals("ANDI")) {
					rd = Integer.parseInt(binary.substring(11, 16), 2);
					RegistersController.setValue(ir.getMEMWBALUOutput(), rd, 1);
					ir.setRn("R" + rd + " = " + ir.getMEMWBALUOutput());
				} else {
					rd = Integer.parseInt(binary.substring(16, 21), 2);
					RegistersController.setValue(ir.getMEMWBALUOutput(), rd, 1);
					ir.setRn("R" + rd + " = " + ir.getMEMWBALUOutput());
				}

			} else if (ins.getCommand().equals("DMULT")) {
				String aluOutput = ir.getMEMWBALUOutput(); // NOT SURE IF MUL.S
															// is correct here
															// TODO
				RegistersController.setValue(aluOutput.substring(0, 8), 32, 3); // HI
				RegistersController.setValue(aluOutput.substring(8, 16), 32, 1); // LO
			} else if (ins.getCommand().equals("MUL.S")) {
				RegistersController.setValue(ir.getMEMWBALUOutput(), 32, 1);
			}
			PipelineMapController.setMapValue("WB", wbLineNumber, cycleNumber);
			if (wbLineNumber == instructions.size() - 1)
				toStop = true;
			wbFinished = wbLineNumber;
			cycleNumberOfLastWBFinish = cycleNumber;
			wbLineNumber++;
		}

	}

	public void fullExecutionRun() {
		while (toStop == false) {
			singleCycleRun();
		}
	}

	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}
}
