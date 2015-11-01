package gui.tablemodels;

import java.math.BigInteger;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang3.StringUtils;

import gui.MainFrame;

public class MemoryTableModel extends AbstractTableModel {

	static String[][] memory;

	public MemoryTableModel() {
		super();
		initMemory();
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
			memory[i][1] = "00000000";
			i++;
		}
	}

	public static String[][] getMemory() {
		return memory;
	}

	@Override
	public int getRowCount() {
		return memory.length;
	}

	@Override
	public int getColumnCount() {
		return memory[0].length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return memory[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 1 || col == 3)
			return true;
		return false;
	}

	public void resetValues() {
		initMemory();
		this.fireTableDataChanged();
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		String val = (String) value;
		try {
			if (val.length() <= 16) {
				val = StringUtils.leftPad(val, 16, "0");

				new BigInteger(val, 16);
				memory[row][col] = val;
				this.fireTableCellUpdated(row, col);
			} else if (val.length() > 16)
				throw new Exception();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), val + " is an invalid memory value!");
		}
	}
}
