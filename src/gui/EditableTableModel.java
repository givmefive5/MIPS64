package gui;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class EditableTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private Vector<Vector<Object>> data;
	private Vector<String> colNames;
	private boolean[] _columnsVisible = { true, true, true, true, true };

	EditableTableModel() {
		this.colNames = new Vector<String>();
		this.data = new Vector<Vector<Object>>();
	}

	EditableTableModel(Vector<String> colnames) {
		this.colNames = colnames;
		this.data = new Vector<Vector<Object>>();
	}

	public void resetTable() {
		this.colNames.removeAllElements();
		this.data.removeAllElements();
	}

	public void setColumnNames(Vector<String> colNames) {
		this.colNames = colNames;
		this.fireTableStructureChanged();
	}

	public void addRow(Vector<Object> data) {
		this.data.add(data);
		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}

	public void removeRowAt(int row) {
		this.data.removeElementAt(row);
		this.fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return this.colNames.size();
	}

	@Override
	public Class<?> getColumnClass(int colNum) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int row, int colNum) {
		switch (colNum) {
		case 2:
			return false;
		default:
			return true;
		}
	}

	@Override
	public String getColumnName(int colNum) {
		return this.colNames.get(colNum);
	}

	@Override
	public int getRowCount() {
		return this.data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Vector<Object> value = this.data.get(row);
		return value.get(col);
	}

	@Override
	public void setValueAt(Object newVal, int row, int col) {
		Vector<Object> aRow = data.elementAt(row);
		aRow.remove(col);
		aRow.insertElementAt(newVal, col);
		fireTableCellUpdated(row, col);
	}

	public void setColumnVisible(int index, boolean visible) {
		this._columnsVisible[index] = visible;
		this.fireTableStructureChanged();
	}

}
