package service.pipeline;

import java.util.NoSuchElementException;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;

public class WBService extends PipelineFunction {

	public WBService(InternalRegister ir) {
		super(ir);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isMemFinished()) {
				Instruction ins = queue.remove();

				System.out.println("WB " + cycleNumber + " " + ins.getLineNumber());
				PipelineMapController.setMapValue("WB", ins.getLineNumber(), cycleNumber);
				ins.setWbFinished(true);
			}
		} catch (NoSuchElementException e) {

		}
	}

}
