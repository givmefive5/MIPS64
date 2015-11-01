package gui.tablemodels;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;

public class CodeTableModel extends AbstractTableModel {
	String[][] codes;

	public CodeTableModel() {
		super();
		initCode();
	}

	private void initCode() {
		codes = new String[2048][4];

		int i = 0;
		String hex = "";
		while (!hex.equals("1FFC")) {
			int mem = i * 4;
			hex = Integer.toHexString(mem).toUpperCase();
			hex = StringUtils.leftPad(hex, 4, "0");
			codes[i][0] = hex;
			codes[i][1] = "00000000";
			i++;
		}
	}

	@Override
	public int getRowCount() {
		return codes.length;
	}

	@Override
	public int getColumnCount() {
		return codes[0].length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return codes[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		codes[row][col] = (String) value;
		this.fireTableCellUpdated(row, col);
	}

	public void resetValues() {
		initCode();
		this.fireTableDataChanged();
	}

	public void setInstruction(Instruction ins, int row) {
		codes[row][1] = ins.getHexOpcode();
		String[] split = ins.getLine().split(";");
		codes[row][2] = split[0];
		codes[row][3] = ins.getComment();
	}
}
