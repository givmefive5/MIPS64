package controller;

import gui.MemoryPanel;
import gui.tablemodels.MemoryTableModel;

public class MemoryController {

	private static MemoryController memoryController;
	private static MemoryPanel memoryPanel;
	private static MemoryTableModel memoryTableModel;

	private MemoryController() {
		memoryPanel = MemoryPanel.getInstance();
		memoryTableModel = memoryPanel.getTableModel();
	}

	public static MemoryController getInstance() {
		if (memoryController == null)
			memoryController = new MemoryController();
		return memoryController;
	}

	public static String getHexWordFromMemory(String baseAddress) {
		Integer address = Integer.parseInt(baseAddress, 16);
		int rowIndex = address / 8 - 1024;
		String word = "";
		word += memoryTableModel.getValueAt(rowIndex + 3, 1);
		word += memoryTableModel.getValueAt(rowIndex + 2, 1);
		word += memoryTableModel.getValueAt(rowIndex + 1, 1);
		word += memoryTableModel.getValueAt(rowIndex, 1);
		return word;
	}

	public static void storeWordToMemory(String hexValue, String baseAddress) {
		Integer address = Integer.parseInt(baseAddress, 16);
		int rowIndex = address / 8 - 1024;
		memoryTableModel.setValueAt(hexValue.substring(0, 2), rowIndex + 3, 1);
		memoryTableModel.setValueAt(hexValue.substring(2, 4), rowIndex + 2, 1);
		memoryTableModel.setValueAt(hexValue.substring(4, 6), rowIndex + 1, 1);
		memoryTableModel.setValueAt(hexValue.substring(6, 8), rowIndex, 1);
	}

	public void resetValues() {
		memoryPanel.resetValues();
	}
}
