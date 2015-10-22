package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import controller.FrameController;

public class MenuBarHandler {
	private static JMenuBar menuBar;
	private static JMenu inputMenu;
	private static JMenuItem fileInputMenuItem;
	private static JMenuItem textInputMenuItem;

	public static JFrame addMenuBarToFrame(JFrame frame) {
		menuBar = new JMenuBar();
		inputMenu = new JMenu("Input");

		fileInputMenuItem = new JMenuItem("Open File");
		textInputMenuItem = new JMenuItem("Open Text Input Console");
		inputMenu.add(fileInputMenuItem);
		inputMenu.add(textInputMenuItem);

		fileInputMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					try {
						FrameController.setInput(selectedFile);
					} catch (IOException e1) {
						System.out.println("Something went wrong when loading file");
						e1.printStackTrace();
					}
				}
			}

		});

		textInputMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Option Pane Text Area Example");

				final SpringLayout layout = new SpringLayout();

				final JPanel panel = new JPanel(layout);
				panel.setPreferredSize(
						new Dimension(800, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() - 200));

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

				int result = JOptionPane.showConfirmDialog(frame, panel, "Input MIPS64 text code",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

				if (result == JOptionPane.YES_OPTION) {
					FrameController.setInput(txtCode.getText());
				} else {
					System.out.println("Canceled JOptionPane for text code input.");
				}
			}

		});

		menuBar.add(inputMenu);

		frame.setJMenuBar(menuBar);
		return frame;
	}
}
