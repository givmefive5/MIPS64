package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InputUIHandler {

	private static JFrame frame = MainFrame.getInstance();

	// UI for fetching input is in MenuBarHandler

	public static void showInputError() {
		JOptionPane.showMessageDialog(frame, "Error found in .....");
	}
}
