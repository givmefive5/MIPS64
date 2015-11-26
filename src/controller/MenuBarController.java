package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import gui.ConsoleInputBox;
import gui.MenuBar;

public class MenuBarController {
	private static MenuBarController menuBarController;
	private static MenuBar menuBar;

	private static JMenuItem fileInputMenuItem;
	private static JMenuItem textInputMenuItem;
	private static JMenuItem runSingleCycleMenuItem;
	private static JMenuItem runFullCycleMenuItem;

	private static FrameController frameController;
	private static PipelineMapController pipelineController;

	private MenuBarController() {
		frameController = FrameController.getInstance();
		pipelineController = pipelineController.getInstance();
		menuBar = MenuBar.getInstance();
		fileInputMenuItem = menuBar.getFileInputMenuItem();
		textInputMenuItem = menuBar.getTextInputMenuItem();
		runSingleCycleMenuItem = menuBar.getRunSingleCycleMenuItem();
		runFullCycleMenuItem = menuBar.getRunFullCycleMenuItem();
		addInputOptionsListeners();
		addRunListeners();
	}

	public static MenuBarController getInstance() {
		if (menuBarController == null)
			menuBarController = new MenuBarController();
		return menuBarController;
	}

	public void setExecuteMenuVisible(boolean flag) {
		menuBar.setRunMenuVisible(flag);
	}

	private void addInputOptionsListeners() {
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
						frameController.setInput(selectedFile);
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
				ConsoleInputBox inputBox = new ConsoleInputBox();
				inputBox.showInputBox("");
			}

		});

	}

	private void addRunListeners() {
		runSingleCycleMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pipelineController.singleCycleRun();
			}

		});

		runFullCycleMenuItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pipelineController.fullExecutionRun();
			}

		});
	}
}
