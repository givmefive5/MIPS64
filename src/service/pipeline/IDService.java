package service.pipeline;

import java.util.NoSuchElementException;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;
import service.RevisedPipelineService;

public class IDService extends PipelineFunction {

	public IDService(InternalRegister ir, RevisedPipelineService pipelineService) {
		super(ir, pipelineService);

	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isIfFinished()) {
				Instruction ins = queue.remove();
				PipelineMapController.setMapValue("ID", ins.getLineNumber(), cycleNumber);
				ins.setIdFinished(true);
				pipelineService.addInstructionTo("EX", ins.getLineNumber());
			}
		} catch (NoSuchElementException e) {

		}
	}

}
