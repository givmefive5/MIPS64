package service.pipeline;

import java.math.BigInteger;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;
import controller.RegistersController;
import service.RevisedPipelineService;

public class WBService extends PipelineFunction {

	RegistersController registersController = RegistersController.getInstance();

	public WBService(InternalRegister ir, RevisedPipelineService pipelineService) {
		super(ir, pipelineService);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isMemFinished()) {
				Instruction ins = queue.remove();

				String command = ins.getCommand().toUpperCase();

				if (command.equals("LW") || command.equals("LWU")) {
					int registerIndex = Integer.parseInt(ins.getRd().substring(1));
					RegistersController.setValue(ir.getMEMWBLMD(), registerIndex, 1);
					ir.setRn("R" + registerIndex + " = " + ir.getMEMWBLMD());
				} else if (command.equals("L.S")) {
					int registerIndex = Integer.parseInt(ins.getRd().substring(1));
					RegistersController.setValue(ir.getMEMWBLMD(), registerIndex, 3);
					ir.setRn("F" + registerIndex + " = " + ir.getMEMWBLMD());
				} else if (command.equals("ADD.S") || command.equals("DADDU") || command.equals("OR")
						|| command.equals("SLT") || command.equals("DSLL") || command.equals("DADDIU")
						|| command.equals("ANDI") || command.equals("MUL.S")) {
					BigInteger integer = new BigInteger(ins.getHexOpcode(), 16);
					String binary = StringUtils.leftPad(integer.toString(2), 32, "0");
					int rd;
					if (command.equals("ADD.S") || command.equals("MUL.S")) {
						rd = Integer.parseInt(binary.substring(21, 26), 2);
						RegistersController.setValue(ir.getMEMWBALUOutput(), rd, 3);
						ir.setRn("F" + rd + " = " + ir.getMEMWBALUOutput());
					} else if (command.equals("DADDIU") || command.equals("ANDI")) {
						rd = Integer.parseInt(binary.substring(11, 16), 2);
						RegistersController.setValue(ir.getMEMWBALUOutput(), rd, 1);
						ir.setRn("R" + rd + " = " + ir.getMEMWBALUOutput());
					} else {
						rd = Integer.parseInt(binary.substring(16, 21), 2);
						RegistersController.setValue(ir.getMEMWBALUOutput(), rd, 1);
						ir.setRn("R" + rd + " = " + ir.getMEMWBALUOutput());
					}

				} else if (command.equals("DMULT")) {
					String aluOutput = ir.getMEMWBALUOutput();
					RegistersController.setValue(aluOutput.substring(0, 8), 32, 3); // HI
					RegistersController.setValue(aluOutput.substring(8, 16), 32, 1); // LO
				}

				PipelineMapController.setMapValue("WB", ins.getLineNumber(), cycleNumber);
				ins.setWbFinished(true);
				ins.setWbFinishedAtCycleNumber(cycleNumber);
				registersController.unlock(ins.getRd(), cycleNumber);
			}
		} catch (NoSuchElementException e) {

		}
	}

}
