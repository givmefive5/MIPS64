package gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class OutputsPanel {
	private static OutputsPanel outputsPanel;
	private static JPanel panel;

	public static OutputsPanel getInstance() {
		if (outputsPanel == null) {
			outputsPanel = new OutputsPanel();
			buildPanel();
		}
		return outputsPanel;
	}

	public JPanel getPanel() {
		return panel;
	}

	private static void buildPanel() {
		panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Outputs"));
	}
}
