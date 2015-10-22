package gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class InternalRegistersPanel {
	private static InternalRegistersPanel internalRegPanel;
	private static JPanel panel;

	public static InternalRegistersPanel getInstance() {
		if (internalRegPanel == null) {
			internalRegPanel = new InternalRegistersPanel();
			buildPanel();
		}
		return internalRegPanel;
	}

	public JPanel getPanel() {
		return panel;
	}

	private static void buildPanel() {
		panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Internal Registers"));
	}
}
