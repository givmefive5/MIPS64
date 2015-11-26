package controller;

import java.util.ArrayList;
import java.util.List;

import Model.Instruction;
import Model.InternalRegister;
import gui.PipelineMapPanel;
import service.RevisedPipelineService;

public class PipelineMapController {
	private static PipelineMapController pipelineMapController;
	private static PipelineMapPanel pipelineMapPanel;
	private String[] codes;
	private static InternalRegistersController internalRegistersController;
	private static RevisedPipelineService pipelineService;

	public PipelineMapController() {
		pipelineMapPanel = PipelineMapPanel.getInstance();
		internalRegistersController = InternalRegistersController.getInstance();
		InternalRegister irs = internalRegistersController.getInternalRegisters();
		pipelineService = new RevisedPipelineService(irs);
	}

	public static PipelineMapController getInstance() {
		if (pipelineMapController == null)
			pipelineMapController = new PipelineMapController();
		return pipelineMapController;
	}

	public void resetValues() {
		pipelineMapPanel.resetValues();
		internalRegistersController.resetValues();
		InternalRegister irs = internalRegistersController.getInternalRegisters();
		pipelineService = new RevisedPipelineService(irs);
	}

	public void setCodeValues(List<Instruction> instructions) {
		List<String> codes = new ArrayList<>();
		for (Instruction ins : instructions) {
			codes.add(ins.getLine());
		}
		this.codes = codes.toArray(new String[codes.size()]);
		pipelineMapPanel.addCodes(this.codes);
		pipelineService.setInstructions(instructions);
	}

	public static void setMapValue(String value, int lineNumber, int cycleNumber) {
		pipelineMapPanel.addCycleValue(value, lineNumber, cycleNumber);
	}

	public void singleCycleRun() {
		pipelineService.singleCycleRun();
		internalRegistersController.fireDataChanged();
	}

	public void fullExecutionRun() {
		pipelineService.fullExecutionRun();
		internalRegistersController.fireDataChanged();
	}
}
