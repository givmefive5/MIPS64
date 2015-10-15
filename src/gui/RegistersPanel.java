package gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class RegistersPanel {

	private static JPanel panel;

	public static JPanel getInstance() {
		if (panel == null) {
			buildPanel();
		}
		return panel;
	}

	private static void buildPanel() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Registers"));
		JTable table = buildTable();
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setSize(panel.getSize());
		panel.add(scrollPane);
	}

	private static JTable buildTable() {
		JTable table = new JTable(33, 4);
		table.setTableHeader(null);
		return table;
	}
}
