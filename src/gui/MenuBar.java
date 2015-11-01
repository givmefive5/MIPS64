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
	private static JMenu runMenu;
	private static JMenuItem runSingleCycleMenuItem;
	private static JMenuItem runFullCycleMenuItem;

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

		runMenu = new JMenu("Execute");
		runSingleCycleMenuItem = new JMenuItem("Single Cycle");
		runFullCycleMenuItem = new JMenuItem("Full Execution");
		runMenu.add(runSingleCycleMenuItem);
		runMenu.add(runFullCycleMenuItem);

		runMenu.setVisible(false);
		jMenuBar.add(runMenu);
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

	public JMenu getRunMenu() {
		return runMenu;
	}

	public JMenuItem getRunSingleCycleMenuItem() {
		return runSingleCycleMenuItem;
	}

	public JMenuItem getRunFullCycleMenuItem() {
		return runFullCycleMenuItem;
	}

	public void setRunMenuVisible(boolean flag) {
		runMenu.setVisible(flag);
	}
}
