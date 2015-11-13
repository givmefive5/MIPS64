package service.pipeline;

import java.util.NoSuchElementException;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;

public class MEMService extends PipelineFunction {
	public MEMService(InternalRegister ir) {
		super(ir);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			Instruction peek = queue.peekFirst();
			if (peek != null && isExFinished(peek)) {
				Instruction ins = queue.remove();

				System.out.println("MEM " + cycleNumber + " " + ins.getLineNumber());
				PipelineMapController.setMapValue("MEM", ins.getLineNumber(), cycleNumber);
				ins.setMemFinished(true);
			}

		} catch (NoSuchElementException e) {

		}
	}

	private boolean isExFinished(Instruction peek) {
		if (peek.getCommand().equals("MUL.S") && peek.getExFinished() == 6)
			return true;
		else if (peek.getCommand().equals("ADD.S") && peek.getExFinished() == 4)
			return true;
		else if (!peek.getCommand().equals("MUL.S") && !peek.getCommand().equals("ADD.S") && peek.getExFinished() == 1)
			return true;
		else
			return false;

	}
}
