package service;

import java.util.List;

import Model.Instruction;
import Model.InternalRegister;
import service.pipeline.EXADDSService;
import service.pipeline.EXMULSService;
import service.pipeline.EXRegService;
import service.pipeline.IDService;
import service.pipeline.IFService;
import service.pipeline.MEMService;
import service.pipeline.WBService;

public class RevisedPipelineService {

	IFService ifService;
	IDService idService;
	EXRegService exRegService;
	EXADDSService exAddsService;
	EXMULSService exMulsService;
	MEMService memService;
	WBService wbService;

	InternalRegister ir;
	List<Instruction> instructions;
	int cycleNumber = 0;

	public RevisedPipelineService(InternalRegister irs) {
		this.ir = irs;
		ifService = new IFService(ir, this);
		idService = new IDService(ir, this);
		exRegService = new EXRegService(ir, this);
		exAddsService = new EXADDSService(ir, this);
		exMulsService = new EXMULSService(ir, this);
		memService = new MEMService(ir, this);
		wbService = new WBService(ir, this);
	}

	public void setInstructions(List<Instruction> instructions) {
		this.instructions = instructions;

		Instruction lastInstruction = instructions.get(instructions.size() - 1);
		if (lastInstruction.getCommand().equals("BEQ") || lastInstruction.getCommand().equals("J")) {
			Instruction nop = new Instruction(instructions.size(), "DADDU r0, r0, r0", "DADDU", "r0", "r0", "r0", null,
					null, null, null, null);
			instructions.add(nop);
		}

		ifService.addInstructionToQueue(instructions.get(0));
	}

	public void singleCycleRun() {
		cycleNumber++;
		System.out.println("Cycle Number " + cycleNumber);
		wbService.run(cycleNumber);
		memService.run(cycleNumber);
		exRegService.run(cycleNumber);
		exMulsService.run(cycleNumber);
		exAddsService.run(cycleNumber);
		idService.run(cycleNumber);
		ifService.run(cycleNumber);
	}

	public void fullExecutionRun() {
		boolean isFinished = false;
		while (!isFinished) {
			singleCycleRun();
			if (ifService.peek() == null && idService.peek() == null && exRegService.peek() == null
					&& exMulsService.peek() == null && exAddsService.peek() == null && memService.peek() == null
					&& wbService.peek() == null)
				isFinished = true;
		}
		// TODO Auto-generated method stub
	}

	public void addInstructionTo(String functionName, int instructionNumber) {
		if (instructionNumber < instructions.size()) {
			Instruction ins = instructions.get(instructionNumber);

			switch (functionName) {
			case ("IF"):
				ifService.addInstructionToQueue(ins);
				break;
			case ("ID"):
				idService.addInstructionToQueue(ins);
				break;
			case ("EX"):
				if (ins.getCommand().toUpperCase().equals("MUL.S"))
					for (int i = 0; i < 6; i++)
						exMulsService.addInstructionToQueue(ins); // 6 times?
				else if (ins.getCommand().toUpperCase().equals("ADD.S"))
					for (int i = 0; i < 4; i++)
						exAddsService.addInstructionToQueue(ins); // 4 times?
				else
					exRegService.addInstructionToQueue(ins);
				break;
			case ("MEM"):
				memService.addInstructionToQueue(ins);
				break;
			case ("WB"):
				wbService.addInstructionToQueue(ins);
				break;
			}
		}

	}

	public Instruction peekAtIDService() {
		return idService.peek();
	}

}
