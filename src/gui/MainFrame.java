package gui;

import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame {

	private static JFrame frame;
	private static JPanel contentPane;

	public static JFrame getInstance() {
		if (frame == null)
			buildFrame();
		return frame;
	}

	private static void buildFrame() {
		frame = new JFrame("MIPS64 Processor");
		frame.setResizable(false);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame = MenuBarHandler.addMenuBarToFrame(frame);

		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(2, 3));
		contentPane.add(RegistersPanel.getInstance().getPanel());
		contentPane.add(MemoryPanel.getInstance().getPanel());
		contentPane.add(PipelinePanel.getInstance().getPanel());
		contentPane.add(InternalRegistersPanel.getInstance().getPanel());
		contentPane.add(OutputsPanel.getInstance().getPanel());
		contentPane.add(CodePanel.getInstance().getPanel());
		frame.setContentPane(contentPane);
	}
}
