package controller;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.commons.lang3.StringUtils;

import gui.MemoryPanel;

public class MemoryController {

	private static MemoryController memoryController;
	private static String[][] memory;
	private static MemoryPanel memoryPanel = MemoryPanel.getInstance();

	private MemoryController() {
		initMemory();
		memoryPanel.initTable(memory);
		addTableListener();
	}

	public static MemoryController getInstance() {
		if (memoryController == null)
			memoryController = new MemoryController();
		return memoryController;
	}

	private void initMemory() {
		memory = new String[1024][2];

		int i = 0;
		String hex = "";
		while (!hex.equals("3FF8")) {
			int mem = (i + 1024) * 8;
			hex = Integer.toHexString(mem).toUpperCase();
			hex = StringUtils.leftPad(hex, 4, "0");
			memory[i][0] = hex;
			memory[i][1] = "0000000000000000";
			i++;
		}
	}

	private void addTableListener() {
		TableModel tableModel = memoryPanel.getTable().getModel();

		tableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent tme) {
				if (tme.getType() == TableModelEvent.UPDATE) {
					int row = tme.getFirstRow();
					int col = tme.getColumn();
					String value = (String) tableModel.getValueAt(tme.getFirstRow(), tme.getColumn());
					memory[row][col] = value;
					System.out.println("New Value : " + memory[row][col]);
				}
			}
		});
	}

	public static void setValue(int row, int column, String value) {

	}
}
