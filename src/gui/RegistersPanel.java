package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import gui.tablemodels.RegisterTableModel;

public class RegistersPanel {

	private static RegistersPanel registersPanel;
	private static JPanel panel;
	private static JTable table;
	private static RegisterTableModel tableModel;

	private RegistersPanel() {
	}

	public static RegistersPanel getInstance() {
		if (registersPanel == null) {
			registersPanel = new RegistersPanel();
			buildPanel();
		}
		return registersPanel;
	}

	public JPanel getPanel() {
		return panel;
	}

	public JTable getTable() {
		return table;
	}

	private static void buildPanel() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Registers"));

		initTable();

	}

	public static void initTable() {
		tableModel = new RegisterTableModel();
		table = new JTable(tableModel);
		table.setFont(new Font("Courier", Font.PLAIN, 12));
		table.setTableHeader(null);
		// table.setShowGrid(false);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(40);
		tcm.getColumn(1).setPreferredWidth(120);
		tcm.getColumn(2).setPreferredWidth(40);
		tcm.getColumn(3).setPreferredWidth(120);

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setSize(panel.getSize());
		panel.add(scrollPane);
	}

	public RegisterTableModel getTableModel() {
		return tableModel;
	}

}
