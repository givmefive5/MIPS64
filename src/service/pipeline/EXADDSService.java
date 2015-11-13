package service.pipeline;

import java.util.NoSuchElementException;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;

public class EXADDSService extends PipelineFunction {

	public EXADDSService(InternalRegister ir) {
		super(ir);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isIdFinished()) {
				Instruction ins = queue.remove();
				System.out.println("EXADDS " + cycleNumber + " " + ins.getLineNumber());
				PipelineMapController.setMapValue("EXADDS", ins.getLineNumber(), cycleNumber);
				ins.setExFinished(ins.getExFinished() + 1);
			}
		} catch (NoSuchElementException e) {

		}
	}

}
