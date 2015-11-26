package controller;

import Model.InternalRegister;
import gui.InternalRegistersPanel;

public class InternalRegistersController {
	private static InternalRegistersController irController;
	private static InternalRegistersPanel irPanel;

	private InternalRegistersController() {
		irPanel = InternalRegistersPanel.getInstance();
	}

	public static InternalRegistersController getInstance() {
		if (irController == null)
			irController = new InternalRegistersController();
		return irController;
	}

	public void resetValues() {
		irPanel.initTableValues();
	}

	public void fireDataChanged() {
		irPanel.fireDataChanged();
	}

	public InternalRegister getInternalRegisters() {
		return irPanel.getInternalRegisters();
	}
}
