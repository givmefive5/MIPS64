package controller;

import java.util.ArrayList;
import java.util.List;

import Model.Instruction;
import gui.PipelineMapPanel;
import service.PipelineCyclesService;

public class PipelineMapController {
	private static PipelineMapController pipelineMapController;
	private static PipelineMapPanel pipelineMapPanel;
	private String[] codes;
	private static InternalRegistersController internalRegistersController;
	private static PipelineCyclesService pipelineCyclesService;

	private PipelineMapController() {
		pipelineMapPanel = PipelineMapPanel.getInstance();
		internalRegistersController = InternalRegistersController.getInstance();
		pipelineCyclesService = new PipelineCyclesService();
	}

	public static PipelineMapController getInstance() {
		if (pipelineMapController == null)
			pipelineMapController = new PipelineMapController();
		return pipelineMapController;
	}

	public void setCodeValues(List<Instruction> instructions) {
		List<String> codes = new ArrayList<>();
		for (Instruction ins : instructions) {
			codes.add(ins.getLine());
		}
		this.codes = codes.toArray(new String[codes.size()]);
		pipelineMapPanel.addCodes(this.codes);
		pipelineCyclesService.setInstructions(instructions);
	}

	public static void setMapValue(String value, int lineNumber, int cycleNumber) {
		pipelineMapPanel.addCycleValue(value, lineNumber, cycleNumber);
	}

	public void singleCycleRun() {
		pipelineCyclesService.singleCycleRun();
		String[] internalRegisterValues = pipelineCyclesService.getValues();
		internalRegistersController.setValues(internalRegisterValues);
	}

	public void fullExecutionRun() {
		pipelineCyclesService.fullExecutionRun();
		String[] internalRegisterValues = pipelineCyclesService.getValues();
		internalRegistersController.setValues(internalRegisterValues);
	}
}
