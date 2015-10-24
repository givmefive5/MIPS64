package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import gui.tablemodels.MemoryTableModel;

public class MemoryPanel {
	private static MemoryPanel memoryPanel;
	private static JPanel panel;
	private static JTable table;

	private MemoryPanel() {
	}

	public static MemoryPanel getInstance() {
		if (memoryPanel == null) {
			memoryPanel = new MemoryPanel();
			buildPanel();
		}
		return memoryPanel;
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
		panel.setBorder(BorderFactory.createTitledBorder("Memory"));
	}

	public void initTable(String[][] memory) {
		String[] columns = { "Address", "Representation" };
		MemoryTableModel tableModel = new MemoryTableModel(memory);
		table = new JTable(tableModel);
		table.setFont(new Font("Courier", Font.PLAIN, 12));
		table.setTableHeader(null);
		// table.setShowGrid(false);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(40);
		tcm.getColumn(1).setPreferredWidth(200);

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setSize(panel.getSize());
		panel.add(scrollPane);
	}

}
