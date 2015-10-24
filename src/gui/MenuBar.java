package gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar {
	private static MenuBar menuBar;
	private static JMenuBar jMenuBar;
	private static JMenu inputMenu;
	private static JMenuItem fileInputMenuItem;
	private static JMenuItem textInputMenuItem;

	private MenuBar() {
	}

	public static MenuBar getInstance() {
		if (menuBar == null) {
			menuBar = new MenuBar();
			initMenuBar();
		}
		return menuBar;
	}

	private static void initMenuBar() {
		jMenuBar = new JMenuBar();
		inputMenu = new JMenu("Input");

		fileInputMenuItem = new JMenuItem("Open File");
		textInputMenuItem = new JMenuItem("Open Text Input Console");
		inputMenu.add(fileInputMenuItem);
		inputMenu.add(textInputMenuItem);

		jMenuBar.add(inputMenu);
	}

	public JMenuBar getjMenuBar() {
		return jMenuBar;
	}

	public JMenu getInputMenu() {
		return inputMenu;
	}

	public JMenuItem getFileInputMenuItem() {
		return fileInputMenuItem;
	}

	public JMenuItem getTextInputMenuItem() {
		return textInputMenuItem;
	}
}
