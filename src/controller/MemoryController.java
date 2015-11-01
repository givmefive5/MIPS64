package controller;

import gui.MemoryPanel;

public class MemoryController {

	private static MemoryController memoryController;
	private static MemoryPanel memoryPanel;

	private MemoryController() {
		memoryPanel = MemoryPanel.getInstance();
	}

	public static MemoryController getInstance() {
		if (memoryController == null)
			memoryController = new MemoryController();
		return memoryController;
	}

	public static String getHexWordFromMemory(String baseAddress) {
		System.out.println("Address :" + baseAddress);
		// TODO
		return "10000000111100001111000011110000";
	}

	public void resetValues() {
		memoryPanel.resetValues();
	}
}
