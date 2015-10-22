package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import Model.Instruction;
import gui.tablemodels.CodeTableModel;

public class CodePanel {
	private static CodePanel codePanel;
	private static JPanel panel;
	private static JTable table;
	private static CodeTableModel tableModel;

	public static CodePanel getInstance() {
		if (codePanel == null) {
			codePanel = new CodePanel();
			buildPanel();
		}
		return codePanel;
	}

	public JPanel getPanel() {
		return panel;
	}

	private static void buildPanel() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createTitledBorder("Code"));
	}

	public void initTable(String[][] codes) {
		String[] columns = { "Address", "Representation", "Label", "Instruction" };
		tableModel = new CodeTableModel(codes, columns);
		table = new JTable(tableModel);
		table.setFont(new Font("Courier", Font.PLAIN, 12));
		// table.setTableHeader(null);
		// table.setShowGrid(false);
		TableColumnModel tcm = table.getColumnModel();
		tcm.getColumn(0).setPreferredWidth(10);
		tcm.getColumn(1).setPreferredWidth(40);
		tcm.getColumn(2).setPreferredWidth(160);
		tcm.getColumn(3).setPreferredWidth(110);

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setSize(panel.getSize());
		panel.add(scrollPane);
	}

	public void setCodeValues(List<Instruction> instructions) {
		for (int i = 0; i < instructions.size(); i++) {
			Instruction ins = instructions.get(i);
			tableModel.setInstruction(ins, i);
		}
		table.repaint();
	}

}
