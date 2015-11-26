package gui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Model.Error;

public class InputErrorDialog extends JDialog {

	private static JTable table;
	private static JFrame frame = MainFrame.getInstance();

	public InputErrorDialog(List<Error> errors) {
		super(frame, "Errors found!");
		buildTable(errors);

		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		scrollPane.setSize(getContentPane().getSize());
		c.add(scrollPane);

		this.setLocationRelativeTo(frame);
		this.pack();
		this.setModal(true);
		this.setVisible(true);
	}

	private static void buildTable(List<Error> errors) {
		String[][] errorsArr = new String[errors.size()][2];
		for (int i = 0; i < errors.size(); i++) {
			Error e = errors.get(i);

			errorsArr[i][0] = e.getLine();
			errorsArr[i][1] = e.getErrorMessage();
		}
		String[] columns = { "Line", "Error" };

		DefaultTableModel tableModel = new DefaultTableModel(errorsArr, columns) {
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		table = new JTable(tableModel);
		table.setFont(new Font("Courier", Font.PLAIN, 12));
	}
}
