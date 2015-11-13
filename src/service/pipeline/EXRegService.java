package service.pipeline;

import java.util.NoSuchElementException;

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
				PipelineMapController.setMapValue("EXReg", ins.getLineNumber(), cycleNumber);
				ins.setExFinished(1);
				pipelineService.addInstructionTo("MEM", ins.getLineNumber());
				pipelineService.addInstructionTo("WB", ins.getLineNumber());
			}
		} catch (NoSuchElementException e) {

		}
	}

}
