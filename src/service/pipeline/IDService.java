package service.pipeline;

import java.util.NoSuchElementException;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;

public class IDService extends PipelineFunction {

	public IDService(InternalRegister ir) {
		super(ir);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isIfFinished()) {
				Instruction ins = queue.remove();
				System.out.println("ID " + cycleNumber + " " + ins.getCommand());
				PipelineMapController.setMapValue("ID", ins.getLineNumber(), cycleNumber);
				ins.setIdFinished(true);
			}
		} catch (NoSuchElementException e) {

		}
	}

}
