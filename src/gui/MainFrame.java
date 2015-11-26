package gui;

import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.FrameController;
import controller.PipelineMapController;

public class MainFrame {

	private static JFrame frame;
	private static JPanel contentPane;

	private static FrameController frameController;
	private static PipelineMapController pipelineMapController;

	public static JFrame getInstance() {
		if (frame == null)
			buildFrame();
		return frame;
	}

	private static void buildFrame() {
		frame = new JFrame("MIPS64 Processor");
		frame.setResizable(false);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setJMenuBar(MenuBar.getInstance().getjMenuBar());

		contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(2, 3));
		contentPane.add(RegistersPanel.getInstance().getPanel());
		contentPane.add(MemoryPanel.getInstance().getPanel());
		contentPane.add(PipelineMapPanel.getInstance().getPanel());
		contentPane.add(InternalRegistersPanel.getInstance().getPanel());
		contentPane.add(OutputsPanel.getInstance().getPanel());
		contentPane.add(CodePanel.getInstance().getPanel());
		frame.setContentPane(contentPane);

		frameController = FrameController.getInstance();
		pipelineMapController = PipelineMapController.getInstance();

		createKeyManager();
	}

	private static void createKeyManager() {
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == 115) { // F4
					if (frameController.getInstructions() != null)
						pipelineMapController.singleCycleRun();
					else
						JOptionPane.showMessageDialog(MainFrame.getInstance(), "Exception: No input found.", "Error",
								JOptionPane.ERROR_MESSAGE);
				}
				return false;
			}

		});
	}

}
