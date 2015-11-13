package gui.tablemodels;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import Model.InternalRegister;
import gui.MainFrame;

public class InternalRegistersTableModel extends AbstractTableModel {
	static InternalRegister irs;

	public InternalRegistersTableModel() {
		super();
		initInternalRegisters();
	}

	private void initInternalRegisters() {
		irs = new InternalRegister();
	}

	@Override
	public int getRowCount() {
		return irs.getIRs().length;
	}

	@Override
	public int getColumnCount() {
		return irs.getIRs()[0].length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (irs.getIRs()[rowIndex][columnIndex] == null)
			return "-";
		else
			return irs.getIRs()[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 1)
			return true;
		return false;
	}

	public void resetInternalRegisters() {
		initInternalRegisters();
		this.fireTableDataChanged();
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		String val = (String) value;
		try {
			if (val == null)
				val = "-";
			irs.getIRs()[row][col] = val;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), val + " is an invalid memory value!");
		}
	}

	public static InternalRegister getInternalRegisters() {
		return irs;
	}

	public void fireDataChanged() {
		this.fireTableDataChanged();
	}
}
