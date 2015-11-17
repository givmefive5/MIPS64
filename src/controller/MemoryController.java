package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import gui.MainFrame;
import gui.MemoryPanel;
import gui.tablemodels.MemoryTableModel;

public class MemoryController {

	private static MemoryController memoryController;
	private static MemoryPanel memoryPanel;
	private static MemoryTableModel memoryTableModel;

	private MemoryController() {
		memoryPanel = MemoryPanel.getInstance();
		memoryTableModel = memoryPanel.getTableModel();
		addListenerToSearchField();
	}

	public static MemoryController getInstance() {
		if (memoryController == null)
			memoryController = new MemoryController();
		return memoryController;
	}

	private void addListenerToSearchField() {
		memoryPanel.getSearchField().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String query = memoryPanel.getSearchField().getText();
				Integer address = Integer.parseInt(query, 16);
				Double rowIndex = address * 1.0 / 8 - 1024;
				if (rowIndex < 0 || rowIndex % 1 != 0) {
					JOptionPane.showMessageDialog(MainFrame.getInstance(),
							"Address " + query + " is not a valid memory location");
				} else {
					JTable table = memoryPanel.getTable();
					table.scrollRectToVisible(table.getCellRect(rowIndex.intValue(), 1, true));
				}

			}
		});
	}

	public static String getHexWordFromMemory(String baseAddress) {
		Integer address = Integer.parseInt(baseAddress, 16);
		Double rowIndex = address * 1.0 / 8 - 1024;
		if (rowIndex < 0 || rowIndex % 1 != 0) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(),
					"Address " + baseAddress + " is not a valid memory location");
			throw new RuntimeException("Invalid memory, Program should stop");
		} else {
			String word = (String) memoryTableModel.getValueAt(rowIndex.intValue(), 1);
			word = word.substring(word.length() - 8, word.length());
			return word;
		}

	}

	public static void storeWordToMemory(String bit32HexValue, String baseAddress) {
		Integer address = Integer.parseInt(baseAddress, 16);
		Double rowIndex = address * 1.0 / 8 - 1024;
		if (rowIndex < 0 || rowIndex % 1 != 0) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(),
					"Address " + baseAddress + " is not a valid memory location");
			throw new RuntimeException("Invalid memory, Program should stop");
		} else {
			memoryTableModel.setValueAt(bit32HexValue, rowIndex.intValue(), 1);
		}
	}

	public void resetValues() {
		memoryPanel.resetValues();
	}
}
