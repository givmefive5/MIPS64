package service.pipeline;

import java.util.LinkedList;

import Model.Instruction;
import Model.InternalRegister;

public abstract class PipelineFunction {

	LinkedList<Instruction> queue = new LinkedList<>();
	InternalRegister ir;
	int cycleNumber = 0;

	public PipelineFunction(InternalRegister ir) {
		this.ir = ir;
	}

	public void addInstructionToQueue(Instruction ins) {
		queue.add(ins);
	}

	public abstract void run(int cycleNumber);

}
