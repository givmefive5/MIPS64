package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import controller.CodeController;

public class CodePanel {
	private static JPanel panel;
	private static JTable table;

	public static JPanel getInstance() {
		if (panel == null) {
			buildPanel();
		}
		return panel;
	}

	private static void buildPanel() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Code"));
		buildTable();
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setSize(panel.getSize());
		panel.add(scrollPane);
	}

	private static void buildTable() {
		String[][] code = CodeController.getCodeValues();
		String[] columns = { "Address", "Representation", "Label", "Instruction" };
		DefaultTableModel tableModel = new DefaultTableModel(code, columns);
		table = new JTable(tableModel);
		table.setFont(new Font("Courier", Font.PLAIN, 12));
		// table.setTableHeader(null);
		// table.setShowGrid(false);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(20);
		tcm.getColumn(1).setPreferredWidth(100);
		tcm.getColumn(2).setPreferredWidth(100);
		tcm.getColumn(3).setPreferredWidth(200);
		addTableListener();
	}

	private static void addTableListener() {
		TableModel tableModel = table.getModel();
		tableModel.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent tme) {
				if (tme.getType() == TableModelEvent.UPDATE) {
					System.out.println("");
					System.out.println("Cell " + tme.getFirstRow() + ", " + tme.getColumn()
							+ " changed. The new value: " + tableModel.getValueAt(tme.getFirstRow(), tme.getColumn()));
				}
			}
		});
	}
}
