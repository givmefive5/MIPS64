package controller;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import gui.RegistersPanel;

public class RegistersController {

	private static RegistersController registersController;
	private static String[][] registers;
	private static RegistersPanel registersPanel = RegistersPanel.getInstance();

	private RegistersController() {
		initRegisters();
		registersPanel.initTable(registers);
		addTableListener();
	}

	public static RegistersController getInstance() {
		if (registersController == null)
			registersController = new RegistersController();
		return registersController;
	}

	private static void initRegisters() {
		registers = new String[33][4];

		for (int i = 0; i < 32; i++) {
			registers[i][0] = "R" + i;
			registers[i][1] = "0000000000000000";
			registers[i][2] = "F" + i;
			registers[i][3] = "0000000000000000";
		}
		registers[32][0] = "LO";
		registers[32][1] = "0000000000000000";
		registers[32][2] = "HI";
		registers[32][3] = "0000000000000000";
	}

	private void addTableListener() {
		TableModel tableModel = registersPanel.getTable().getModel();
		tableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent tme) {
				if (tme.getType() == TableModelEvent.UPDATE) {
					int row = tme.getFirstRow();
					int col = tme.getColumn();
					String value = (String) tableModel.getValueAt(tme.getFirstRow(), tme.getColumn());
					registers[row][col] = value;
					System.out.println("New Value : " + registers[row][col]);
				}
			}
		});
	}

}
