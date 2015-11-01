package controller;

import gui.MemoryPanel;
import gui.tablemodels.MemoryTableModel;

public class MemoryController {

	private static MemoryController memoryController;
	private static String[][] memory;
	private static MemoryPanel memoryPanel;

	private MemoryController() {
		memoryPanel = MemoryPanel.getInstance();
	}

	public static MemoryController getInstance() {
		if (memoryController == null)
			memoryController = new MemoryController();
		return memoryController;
	}

	private void getMemoryFromTableModel() {
		memory = MemoryTableModel.getMemory();
	}
}
