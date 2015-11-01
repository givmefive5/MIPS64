package service;

import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;
import Model.InternalRegister;
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

	boolean toStop = false;

	public PipelineCyclesService() {
		ir = new InternalRegister();
		// TODO: Using the line numbers to check whether or not an instruction
		// needs to stall or not.
	}

	public void singleCycleRun() {
		if (toStop == false) {
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
		return false;
	}

	private void callIF() {
		if (!shouldStall("IF", ifLineNumber) && ifLineNumber < instructions.size()) {
			Instruction ins = instructions.get(ifLineNumber);
			ir.setIFIDIR(ins.getHexOpcode());
			if (ins.getCommand().equals("BEQ") || ins.getCommand().equals("J")) {
				// TODO
			} else {
				String npc = StringUtils.leftPad(Integer.toString((ins.getLineNumber() + 1) * 4), 16, "0");
				ir.setIFIDNPC(npc);
				ir.setPC(npc);
				idLineNumber = ifLineNumber;
			}
			PipelineMapController.setMapValue("IF", ifLineNumber, cycleNumber);
			ifLineNumber++;
		}
	}

	private void callID() {
		if (idLineNumber >= 0) {
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
			idLineNumber = -1;
		}

	}

	private void callEX() {
		if (exLineNumber >= 0) {
			Instruction ins = instructions.get(exLineNumber);

			String command = ins.getCommand().toUpperCase();
			String aluOutput = "";
			String cond = "0";

			BigInteger imm = new BigInteger(ir.getIDEXIMM(), 16);

			if (command.equals("LW") || command.equals("LWU") || command.equals("SW")) {
				BigInteger a = new BigInteger(ir.getIDEXA(), 16);
				BigInteger tempALU = a.add(imm);
				aluOutput = tempALU.toString(16).toUpperCase();
			} else if (command.equals("L.S") || command.equals("S.S")) {
				BigInteger a = new BigInteger(ir.getIDEXA(), 16);
				aluOutput = a.add(imm).toString(16).toUpperCase();
			} else if (command.equals("MUL.S") || command.equals("ADD.S")) {
				Float a = Float.parseFloat(ir.getIDEXA());
				Float b = Float.parseFloat(ir.getIDEXB());
				if (command.equals("MUL.S"))
					aluOutput = Float.toString(a * b);
				else if (command.equals("ADD.S"))
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
			}

			else if (command.equals("DSLL") || command.equals("ANDI") || command.equals("DADDIU")) {

				if (command.equals("DSLL")) {

					int a = (int) Long.parseLong(ir.getIDEXA(), 16);
					int immediate = Integer.parseInt(ir.getIDEXIMM(), 16);
					System.out.println(a + " " + immediate);
					int shifted = a << immediate;
					aluOutput = Integer.toHexString(shifted);
				}

				else if (command.equals("ANDI")) {
					BigInteger a = new BigInteger(ir.getIDEXA(), 16);
					aluOutput = a.or(imm).toString(16).toUpperCase();
				}

				else if (command.equals("DADDIU")) {
					BigInteger a = new BigInteger(ir.getIDEXA(), 16);
					aluOutput = a.add(imm).toString(16).toUpperCase();
				}
			}
			aluOutput = StringUtils.leftPad(aluOutput, 16, "0");
			ir.setEXMEMALUOutput(aluOutput);
			ir.setEXMEMCond(cond);
			ir.setEXMEMIR(ir.getIDEXIR());
			ir.setEXMEMB(ir.getIDEXB());

			PipelineMapController.setMapValue("EX", exLineNumber, cycleNumber);
			memLineNumber = exLineNumber;
			exLineNumber = -1;
		}

	}

	private void callMEM() {
		if (memLineNumber >= 0) {
			Instruction ins = instructions.get(memLineNumber);
			PipelineMapController.setMapValue("MEM", memLineNumber, cycleNumber);
			wbLineNumber = memLineNumber;
			memLineNumber = -1;
		}
	}

	private void callWB() {
		// TODO Auto-generated method stub
		if (wbLineNumber >= 0) {
			PipelineMapController.setMapValue("WB", wbLineNumber, cycleNumber);
			if (wbLineNumber == instructions.size() - 1)
				toStop = true;

		}

	}

	public void fullExecutionRun() {

	}

	public String[] getValues() {
		return ir.getValues();
	}

	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;
	}
}
