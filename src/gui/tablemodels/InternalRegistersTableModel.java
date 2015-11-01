package gui.tablemodels;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import gui.MainFrame;

public class InternalRegistersTableModel extends AbstractTableModel {
	static String[][] irs;

	public InternalRegistersTableModel() {
		super();
		initInternalRegisters();
	}

	private void initInternalRegisters() {
		irs = new String[16][2];
		irs[0][0] = "IF/ID.IR";
		irs[1][0] = "IF/ID.NPC";
		irs[2][0] = "PC";
		irs[3][0] = "ID/EX.A";
		irs[4][0] = "ID/EX.B";
		irs[5][0] = "ID/EX.IMM";
		irs[6][0] = "ID/EX.IR";
		irs[7][0] = "ID/EX.NPC";
		irs[8][0] = "EX/MEM.ALUOutput";
		irs[9][0] = "EX/MEM.COND";
		irs[10][0] = "EX/MEM.IR";
		irs[11][0] = "EX/MEM.B";
		irs[12][0] = "MEM/WB.LMD";
		irs[13][0] = "MEM/WB.IR";
		irs[14][0] = "MEM/WB.ALUOutput";
		irs[15][0] = "Rn";
		for (int i = 0; i < irs.length; i++) {
			irs[i][1] = "-";
		}
	}

	@Override
	public int getRowCount() {
		return irs.length;
	}

	@Override
	public int getColumnCount() {
		return irs[0].length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return irs[rowIndex][columnIndex];
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

	public void setValues(String[] values) {
		for (int i = 0; i < values.length; i++) {
			if (values[i] == null)
				values[i] = "-";
			irs[i][1] = values[i];
		}
		this.fireTableDataChanged();
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		String val = (String) value;
		try {
			if (val == null)
				val = "-";
			irs[row][col] = val;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.getInstance(), val + " is an invalid memory value!");
		}
	}

	public static String[][] getInternalRegisters() {
		return irs;
	}
}
