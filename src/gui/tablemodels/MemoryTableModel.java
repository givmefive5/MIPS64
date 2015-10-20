package gui.tablemodels;

import java.math.BigInteger;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang3.StringUtils;

import gui.MainFrame;

public class MemoryTableModel extends AbstractTableModel {

	String[][] table;

	public MemoryTableModel(String[][] memory, String[] columns) {
		super();
		table = memory;
	}

	@Override
	public int getRowCount() {
		return table.length;
	}

	@Override
	public int getColumnCount() {
		return table[0].length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return table[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 1 || col == 3)
			return true;
		return false;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		String val = (String) value;
		try {
			if (val.length() <= 16) {
				val = StringUtils.leftPad(val, 16, "0");

				new BigInteger(val, 16);
				table[row][col] = val;
				this.fireTableCellUpdated(row, col);
			} else if (val.length() > 16)
				throw new Exception();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), val + " is an invalid memory value!");
		}
	}
}
