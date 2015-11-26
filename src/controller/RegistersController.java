package controller;

import gui.RegistersPanel;
import gui.tablemodels.RegisterTableModel;

public class RegistersController {

	private static RegistersController registersController;
	private static RegistersPanel registersPanel;
	private static RegisterTableModel registersTableModel;
	private static boolean[][] locks;

	private RegistersController() {
		registersPanel = RegistersPanel.getInstance();
		registersTableModel = registersPanel.getTableModel();
		initLocks();
	}

	private void initLocks() {
		locks = new boolean[32][2];
		for (int i = 0; i < 32; i++) {
			locks[i][0] = false;
			locks[i][1] = false;
		}
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

	public boolean isLocked(String r) {
		if (r != null) {
			if (r.substring(0, 1).toLowerCase().equals("r")) {
				return registersTableModel.isLocked(Integer.parseInt(r.substring(1)), 1);
			} else if (r.substring(0, 1).toLowerCase().equals("f")) {
				return registersTableModel.isLocked(Integer.parseInt(r.substring(1)), 3);
			}
		}
		return false;

	}

	public void lock(String rd) {
		if (rd != null)
			if (!rd.substring(1).equals("0")) {
				if (rd.substring(0, 1).toLowerCase().equals("r")) {
					registersTableModel.lock(Integer.parseInt(rd.substring(1)), 1);
				} else if (rd.substring(0, 1).toLowerCase().equals("f")) {
					registersTableModel.lock(Integer.parseInt(rd.substring(1)), 3);
				}
			}

	}

	public void unlock(String rd, int cycleNumber) {
		if (rd != null)
			if (!rd.substring(1).equals("0")) {
				if (rd.substring(0, 1).toLowerCase().equals("r")) {
					registersTableModel.unlock(Integer.parseInt(rd.substring(1)), 1, cycleNumber);
				} else if (rd.substring(0, 1).toLowerCase().equals("f")) {
					registersTableModel.unlock(Integer.parseInt(rd.substring(1)), 3, cycleNumber);
				}
			}
	}

	public int getCycleNumberReleased(String r) {
		if (r != null)
			if (r.substring(0, 1).toLowerCase().equals("r")) {
				return registersTableModel.getCycleNumberReleased(Integer.parseInt(r.substring(1)), 1);
			} else if (r.substring(0, 1).toLowerCase().equals("f")) {
				return registersTableModel.getCycleNumberReleased(Integer.parseInt(r.substring(1)), 3);
			}
		return -1;
	}
}
