package controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import exceptions.InvalidFormatException;
import gui.InputUIHandler;
import service.MIPS64Checker;

public class InputHandler {

	private static String input;

	public static void setInput(File f) throws IOException {
		input = new String(Files.readAllBytes(f.toPath()));
		handleInput();
	}

	public static void setInput(String text) {
		input = text;
		handleInput();
	}

	private static void handleInput() {
		System.out.println(input);
		try {
			MIPS64Checker.parse(input);
		} catch (InvalidFormatException e) {
			InputUIHandler.showInputError();
		}
	}
}
