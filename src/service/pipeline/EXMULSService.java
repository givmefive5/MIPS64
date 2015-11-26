package service.pipeline;

import java.math.BigInteger;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;
import controller.RegistersController;
import service.RevisedPipelineService;

public class EXMULSService extends PipelineFunction {

	public EXMULSService(InternalRegister ir, RevisedPipelineService pipelineService) {
		super(ir, pipelineService);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isIdFinished()) {
				Instruction ins = queue.remove();

				String cond = "0";
				String aluOutput;

				BigInteger integer = new BigInteger(ins.getHexOpcode(), 16);
				String binary = StringUtils.leftPad(integer.toString(2), 32, "0");
				int regA = Integer.parseInt(binary.substring(16, 21), 2);
				int regB = Integer.parseInt(binary.substring(11, 16), 2);
				String aVal = RegistersController.getInstance().getValue(regA, 3);
				String bVal = RegistersController.getInstance().getValue(regB, 3);
				Float a = Float.parseFloat(aVal);
				Float b = Float.parseFloat(bVal);
				aluOutput = Float.toString(a * b);

				ir.setEXMEMALUOutputMULS(aluOutput);
				ir.setEXMEMCondMULS(cond);
				ir.setEXMEMIRMULS(ir.getIDEXIR());
				ir.setEXMEMBMULS(ir.getIDEXB());

				PipelineMapController.setMapValue("EXMULS", ins.getLineNumber(), cycleNumber);
				ins.setExFinished(ins.getExFinished() + 1);
				if (ins.getExFinished() == 6) {
					pipelineService.addInstructionTo("MEM", ins.getLineNumber());
					pipelineService.addInstructionTo("WB", ins.getLineNumber());
				}
			}
		} catch (NoSuchElementException e) {

		}
	}

}
