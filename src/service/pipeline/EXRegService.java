package service.pipeline;

import java.util.NoSuchElementException;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;

public class EXRegService extends PipelineFunction {

	public EXRegService(InternalRegister ir) {
		super(ir);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(int cycleNumber) {
		try {
			if (queue.peekFirst() != null && queue.peekFirst().isIdFinished()) {
				Instruction ins = queue.remove();
				System.out.println("EXREG " + cycleNumber + " " + ins.getLineNumber());
				PipelineMapController.setMapValue("EXReg", ins.getLineNumber(), cycleNumber);
				ins.setExFinished(1);
			}
		} catch (NoSuchElementException e) {

		}
	}

}
