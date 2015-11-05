package gui.tablemodels;

import java.math.BigInteger;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang3.StringUtils;

import gui.MainFrame;

public class RegisterTableModel extends AbstractTableModel {

	static String[][] registers;

	public RegisterTableModel() {
		super();
		initRegisters();
	}

	private void initRegisters() {
		registers = new String[33][4];

		for (int i = 0; i < 32; i++) {
			registers[i][0] = "R" + i;
			registers[i][1] = "0000000000000000";
			registers[i][2] = "F" + i;
			registers[i][3] = "0.0";
		}
		registers[32][0] = "LO";
		registers[32][1] = "00000000";
		registers[32][2] = "HI";
		registers[32][3] = "00000000";

	}

	public static String[][] getRegisters() {
		return registers;
	}

	@Override
	public int getRowCount() {
		return registers.length;
	}

	@Override
	public int getColumnCount() {
		return registers[0].length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return registers[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 1 || col == 3)
			return true;
		return false;
	}

	public void resetValues() {
		initRegisters();
		this.fireTableDataChanged();
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		String val = (String) value;
		if (row == 0)
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "Registers R0 and F0 can not be edited.");
		else if (row == 32) {
			val = StringUtils.leftPad(val, 8, "0");
			try {
				new BigInteger(val, 16);
				registers[row][col] = val;
				this.fireTableCellUpdated(row, col);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(MainFrame.getInstance(), val + " is an invalid register value!");
			}
		} else if (val.length() <= 16 && col == 1) {
			val = StringUtils.leftPad(val, 16, "0");
			try {
				new BigInteger(val, 16);
				registers[row][col] = val;
				this.fireTableCellUpdated(row, col);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(MainFrame.getInstance(), val + " is an invalid register value!");
			}
		} else if (val.length() > 16 && col == 1)
			JOptionPane.showMessageDialog(MainFrame.getInstance(), val + " is an invalid register value!");
		else if (col == 3) {
			try {
				Double.parseDouble(val);
				registers[row][col] = val;
				this.fireTableCellUpdated(row, col);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(MainFrame.getInstance(), val + " is an invalid floating point value!");
			}
		}
	}
}
