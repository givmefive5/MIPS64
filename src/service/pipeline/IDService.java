package service.pipeline;

import java.util.NoSuchElementException;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;
import controller.RegistersController;
import service.RevisedPipelineService;

public class IDService extends PipelineFunction {

	RegistersController registersController = RegistersController.getInstance();

	public IDService(InternalRegister ir, RevisedPipelineService pipelineService) {
		super(ir, pipelineService);

	}

	@Override
	public void run(int cycleNumber) {
		try {
			Instruction peek = queue.peek();
			if (peek != null
					&& (registersController.isLocked(peek.getRs()) || registersController.isLocked(peek.getRt())
							|| registersController.getCycleNumberReleased(peek.getRs()) == cycleNumber
							|| registersController.getCycleNumberReleased(peek.getRt()) == cycleNumber)) {

			} else if (peek != null && peek.isIfFinished()) {
				Instruction ins = queue.remove();
				PipelineMapController.setMapValue("ID", ins.getLineNumber(), cycleNumber);
				ins.setIdFinished(true);
				pipelineService.addInstructionTo("EX", ins.getLineNumber());
				registersController.lock(ins.getRd());
			}
		} catch (NoSuchElementException e) {

		}
	}

	public Instruction peek() {
		return queue.peek();
	}

}
