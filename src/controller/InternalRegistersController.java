package controller;

import gui.InternalRegistersPanel;

public class InternalRegistersController {
	private static InternalRegistersController irController;
	private static String[][] internalRegisters;
	private static InternalRegistersPanel irPanel;

	private InternalRegistersController() {
		irPanel = InternalRegistersPanel.getInstance();
	}

	public static InternalRegistersController getInstance() {
		if (irController == null)
			irController = new InternalRegistersController();
		return irController;
	}

	public void setValues(String[] irValues) {
		irPanel.setValues(irValues);
	}
}
