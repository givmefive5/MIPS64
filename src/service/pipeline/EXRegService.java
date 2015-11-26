package service.pipeline;

import java.math.BigInteger;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;
import controller.RegistersController;
import service.BinaryHexConverter;
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
						BigInteger integer = new BigInteger(ins.getHexOpcode(), 16);
						String binary = StringUtils.leftPad(integer.toString(2), 32, "0");
						int shf = Integer.parseInt(binary.substring(21, 26), 2);
						int regA = Integer.parseInt(binary.substring(11, 16), 2);
						String aVal = RegistersController.getInstance().getValue(regA, 1);
						String aBin = BinaryHexConverter.convertHexToBinary(aVal, 64);

						String shifted = aBin.substring(shf);
						String padRight = StringUtils.leftPad("", shf, "0");
						shifted += padRight;
						aluOutput = BinaryHexConverter.convertBinaryToHex(shifted, 16);
					} else if (command.equals("ANDI")) {
						BigInteger a = new BigInteger(ir.getIDEXA(), 16);
						aluOutput = a.and(imm).toString(16).toUpperCase();
					} else if (command.equals("DADDIU")) {
						BigInteger a = new BigInteger(ir.getIDEXA(), 16);
						aluOutput = a.add(imm).toString(16).toUpperCase();
					}
					aluOutput = StringUtils.leftPad(aluOutput, 16, "0");
				}

				if (command.equals("J"))
					cond = "1";

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
