package gui.tablemodels;

import javax.swing.table.AbstractTableModel;

public class PipelineMapTableModel extends AbstractTableModel {
	String[][] pipeline;

	public PipelineMapTableModel() {
		super();
		initPipelineMap();
	}

	private void initPipelineMap() {
		pipeline = new String[10][10];
	}

	@Override
	public int getRowCount() {
		return pipeline.length;
	}

	@Override
	public int getColumnCount() {
		return pipeline[0].length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return pipeline[rowIndex][columnIndex];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void resetPipelineMap() {
		initPipelineMap();
		this.fireTableDataChanged();
	}

	public void addCodes(String[] codes) {
		String[][] newTable = new String[codes.length][10];
		for (int i = 0; i < codes.length; i++) {
			newTable[i][0] = codes[i];
		}
		pipeline = newTable;
		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}

	@Override
	public String getColumnName(int index) {
		if (index == 0)
			return "Code";
		else
			return "Cycle " + index;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		int newRowSize = pipeline.length;
		int newColumnSize = pipeline[0].length;
		boolean hasChanged = false;
		if (row + 1 > newRowSize) {
			newRowSize = row + 1;
			hasChanged = true;
		}
		if (col + 1 > newColumnSize) {
			newColumnSize = col + 1;
			hasChanged = true;
		}

		if (hasChanged == true) {
			String[][] newPipeline = new String[newRowSize][newColumnSize];
			for (int i = 0; i < pipeline.length; i++) {
				for (int j = 0; j < pipeline[0].length; j++) {
					newPipeline[i][j] = pipeline[i][j];
				}
			}
			pipeline = newPipeline;
		}
		pipeline[row][col] = (String) value;

		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}
}