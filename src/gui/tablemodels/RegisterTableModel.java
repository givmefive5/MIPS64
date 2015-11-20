package gui.tablemodels;

import java.math.BigInteger;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang3.StringUtils;

import Model.Register;
import gui.MainFrame;

public class RegisterTableModel extends AbstractTableModel {

	static Register[][] registers;

	public RegisterTableModel() {
		super();
		initRegisters();
	}

	private void initRegisters() {
		registers = new Register[33][4];

		for (int i = 0; i < 32; i++) {
			registers[i][0] = new Register("R" + i);
			registers[i][1] = new Register("0000000000000000");
			registers[i][2] = new Register("F" + i);
			registers[i][3] = new Register("0.0");
		}
		registers[32][0] = new Register("LO");
		registers[32][1] = new Register("0000000000000000");
		registers[32][2] = new Register("HI");
		registers[32][3] = new Register("0000000000000000");

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
		return registers[rowIndex][columnIndex].getValue();
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
				registers[row][col].setValue(val);
				this.fireTableCellUpdated(row, col);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(MainFrame.getInstance(), val + " is an invalid register value!");
			}
		} else if (val.length() <= 16 && col == 1) {
			val = StringUtils.leftPad(val, 16, "0");
			try {
				new BigInteger(val, 16);
				registers[row][col].setValue(val);
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
				registers[row][col].setValue(val);
				this.fireTableCellUpdated(row, col);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(MainFrame.getInstance(), val + " is an invalid floating point value!");
			}
		}
	}

	public boolean isLocked(int row, int col) {
		return registers[row][col].isLocked();
	}

	public void lock(int row, int col) {
		registers[row][col].setLocked(true);
	}

	public void unlock(int row, int col, int cycleNumber) {
		registers[row][col].setLocked(false);
		registers[row][col].setCycleNumberReleased(cycleNumber);
	}

	public int getCycleNumberReleased(int row, int col) {
		return registers[row][col].getCycleNumberReleased();
	}

}
