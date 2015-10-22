package controller;

import javax.swing.JFrame;

import gui.MainFrame;

public class Runnable {

	private static FrameController frameController;

	public static void main(String[] args) {
		JFrame frame = MainFrame.getInstance();

		frameController = FrameController.getInstance();

		frame.setVisible(true);

	}
}
