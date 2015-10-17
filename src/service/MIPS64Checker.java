package service;

import java.util.List;

import exceptions.InvalidFormatException;

public class MIPS64Checker {

	public static List<String> parse(String text) throws InvalidFormatException {
		String[] lines = text.split("\n");
		for (int i = 0; i < lines.length; i++) {
			System.out.println(i + ". - " + lines[i]);
		}
		return null;
	}
}
