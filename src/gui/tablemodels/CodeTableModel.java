package gui.tablemodels;

import javax.swing.table.AbstractTableModel;

import Model.Instruction;

public class CodeTableModel extends AbstractTableModel {
	String[][] table;

	public CodeTableModel(String[][] memory, String[] columns) {
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
		return false;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		table[row][col] = (String) value;
		this.fireTableCellUpdated(row, col);
	}

	public void setInstruction(Instruction ins, int row) {
		table[row][1] = ins.getHexOpcode();
		String[] split = ins.getLine().split(";");
		table[row][2] = split[0];
		table[row][3] = ins.getComment();
	}
}
