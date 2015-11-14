package service.pipeline;

import java.math.BigInteger;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;
import service.RevisedPipelineService;

public class EXRegService extends PipelineFunction {

	public EXRegService(InternalRegister ir, RevisedPipelineService pipelineService) {
		super(ir, pipelineService);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isIdFinished()) {
				Instruction ins = queue.remove();

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
					else if (command.equals("ADD.S")) // TODO requires 4 EX
														// cycles
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

				PipelineMapController.setMapValue("EXReg", ins.getLineNumber(), cycleNumber);
				ins.setExFinished(1);
				pipelineService.addInstructionTo("MEM", ins.getLineNumber());
				pipelineService.addInstructionTo("WB", ins.getLineNumber());
			}
		} catch (NoSuchElementException e) {

		}
	}

}
