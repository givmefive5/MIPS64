package service.pipeline;

import java.util.LinkedList;

import Model.Instruction;
import Model.InternalRegister;
import service.RevisedPipelineService;

public abstract class PipelineFunction {

	RevisedPipelineService pipelineService;

	LinkedList<Instruction> queue = new LinkedList<>();
	InternalRegister ir;
	int cycleNumber = 0;

	public PipelineFunction(InternalRegister ir, RevisedPipelineService pipelineService) {
		this.ir = ir;
		this.pipelineService = pipelineService;
	}

	public void addInstructionToQueue(Instruction ins) {
		queue.add(ins);
	}

	public abstract void run(int cycleNumber);

}
