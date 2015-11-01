package controller;

import gui.RegistersPanel;
import gui.tablemodels.RegisterTableModel;

public class RegistersController {

	private static RegistersController registersController;
	private static RegistersPanel registersPanel;
	private static RegisterTableModel registersTableModel;

	private RegistersController() {
		registersPanel = RegistersPanel.getInstance();
		registersTableModel = registersPanel.getTableModel();
	}

	public static RegistersController getInstance() {
		if (registersController == null)
			registersController = new RegistersController();
		return registersController;
	}

	public static void setValue(String value, int row, int col) {
		registersTableModel.setValueAt(value, row, col);
	}

	public String getValue(int row, int col) {
		return (String) registersTableModel.getValueAt(row, col);
	}

	public void resetValues() {
		registersTableModel.resetValues();
	}
}
