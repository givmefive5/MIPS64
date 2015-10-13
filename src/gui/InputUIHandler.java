package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class InputUIHandler {

	private static JFrame frame = MainFrame.getInstance();

	public static void showInputError() {
		JOptionPane.showMessageDialog(frame, "Error found in .....");
	}
}
