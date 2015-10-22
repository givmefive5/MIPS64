package gui;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PipelinePanel {
	private static PipelinePanel pipelinePanel;
	private static JPanel panel;

	public static PipelinePanel getInstance() {
		if (pipelinePanel == null) {
			pipelinePanel = new PipelinePanel();
			buildPanel();
		}
		return pipelinePanel;
	}

	public JPanel getPanel() {
		return panel;
	}

	private static void buildPanel() {
		panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Pipeline"));
	}
}
