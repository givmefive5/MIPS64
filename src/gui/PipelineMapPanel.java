package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import gui.tablemodels.PipelineMapTableModel;

public class PipelineMapPanel {
	private static PipelineMapPanel pipelinePanel;
	private static JPanel panel;
	private static JTable table;
	private static JScrollPane scrollPane;

	private PipelineMapPanel() {
	}

	public static PipelineMapPanel getInstance() {
		if (pipelinePanel == null) {
			pipelinePanel = new PipelineMapPanel();
			buildPanel();
		}
		return pipelinePanel;
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
		panel.setBorder(BorderFactory.createTitledBorder("Pipeline Map"));

		initTable();
	}

	public static void initTable() {
		PipelineMapTableModel tableModel = new PipelineMapTableModel();
		table = new JTable(tableModel);
		table.setFont(new Font("Courier", Font.PLAIN, 12));

		TableColumnModel tcm = table.getColumnModel();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setSize(panel.getSize());
		panel.add(scrollPane);

	}

	public void addCodes(String[] codes) {
		PipelineMapTableModel tableModel = (PipelineMapTableModel) table.getModel();
		tableModel.addCodes(codes);

	}

	public void addCycleValue(String val, int lineNumber, int cycleNumber) {
		TableModel tableModel = table.getModel();
		tableModel.setValueAt(val, lineNumber, cycleNumber);
	}
}
