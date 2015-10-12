import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
						InputHandler.setInput(selectedFile);
					} catch (IOException e1) {
						System.out.println("Something went wrong when loading file");
						e1.printStackTrace();
					}
				}
			}

		});

		menuBar.add(inputMenu);

		frame.setJMenuBar(menuBar);
		return frame;
	}
}
