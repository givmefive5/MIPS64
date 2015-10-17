package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JFrame;

import exceptions.InvalidFormatException;
import gui.InputUIHandler;
import gui.MainFrame;
import service.MIPS64Checker;

public class MainController {

	private static String input;

	public static void main(String[] args) {
		JFrame frame = MainFrame.getInstance();
		frame.setVisible(true);
	}

	public static void setInput(File f) throws IOException {
		input = new String(Files.readAllBytes(f.toPath()));
		handleInput();
	}

	public static void setInput(String text) {
		input = text;
		handleInput();
	}

	private static void handleInput() {
		try {
			MIPS64Checker.parse(input);
		} catch (InvalidFormatException e) {
			InputUIHandler.showInputError();
		}
	}

}
