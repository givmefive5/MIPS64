package service.pipeline;

import java.util.NoSuchElementException;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;

public class EXMULSService extends PipelineFunction {

	public EXMULSService(InternalRegister ir) {
		super(ir);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isIdFinished()) {
				Instruction ins = queue.remove();
				System.out.println("EXMULS " + cycleNumber + " " + ins.getLineNumber());
				PipelineMapController.setMapValue("EXMULS", ins.getLineNumber(), cycleNumber);
				ins.setExFinished(ins.getExFinished() + 1);
			}
		} catch (NoSuchElementException e) {

		}
	}

}
