package service.pipeline;

import java.util.NoSuchElementException;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;
import service.RevisedPipelineService;

public class EXADDSService extends PipelineFunction {

	public EXADDSService(InternalRegister ir, RevisedPipelineService pipelineService) {
		super(ir, pipelineService);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isIdFinished()) {
				Instruction ins = queue.remove();
				PipelineMapController.setMapValue("EXADDS", ins.getLineNumber(), cycleNumber);
				ins.setExFinished(ins.getExFinished() + 1);
				if (ins.getExFinished() == 4) {
					pipelineService.addInstructionTo("MEM", ins.getLineNumber());
					pipelineService.addInstructionTo("WB", ins.getLineNumber());
				}
			}
		} catch (NoSuchElementException e) {

		}
	}

}
