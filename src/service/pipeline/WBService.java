package service.pipeline;

import java.util.NoSuchElementException;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;
import service.RevisedPipelineService;

public class WBService extends PipelineFunction {

	public WBService(InternalRegister ir, RevisedPipelineService pipelineService) {
		super(ir, pipelineService);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isMemFinished()) {
				Instruction ins = queue.remove();
				PipelineMapController.setMapValue("WB", ins.getLineNumber(), cycleNumber);
				ins.setWbFinished(true);
				ins.setWbFinishedAtCycleNumber(cycleNumber);
			}
		} catch (NoSuchElementException e) {

		}
	}

}
