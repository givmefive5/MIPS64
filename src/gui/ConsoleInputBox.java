package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import controller.FrameController;

public class ConsoleInputBox {

	FrameController frameController = FrameController.getInstance();

	public void showInputBox() {
		JFrame frame = new JFrame("Input Code");

		final SpringLayout layout = new SpringLayout();

		final JPanel panel = new JPanel(layout);
		panel.setPreferredSize(new Dimension(800, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 200));

		JLabel lblCode = new JLabel("Code:");
		panel.add(lblCode);
		JTextArea txtCode = new JTextArea();
		txtCode.setBorder(BorderFactory.createLineBorder(Color.black));
		txtCode.setLineWrap(true);
		txtCode.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(txtCode, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(800, 800));
		panel.add(scrollPane);

		layout.putConstraint(SpringLayout.WEST, lblCode, 0, SpringLayout.WEST, panel);
		layout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, lblCode);

		int result = JOptionPane.showConfirmDialog(frame, panel, "Input MIPS64 text code", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			frameController.setInput(txtCode.getText());
		} else {
			System.out.println("Canceled JOptionPane for text code input.");
		}
	}
}
