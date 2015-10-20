package controller;

public class RegistersController {
	private static String[][] registers;

	public static String[][] getRegisterValues() {
		if (registers == null) {
			initRegister();
		}
		return registers;
	}

	private static void initRegister() {
		registers = new String[33][4];

		for (int i = 0; i < 32; i++) {
			registers[i][0] = "R" + i;
			registers[i][1] = "0000000000000000";
			registers[i][2] = "F" + i;
			registers[i][3] = "0000000000000000";
		}
		registers[32][0] = "LO";
		registers[32][1] = "0000000000000000";
		registers[32][2] = "HI";
		registers[32][3] = "0000000000000000";
	}

	public static void setValue(int row, int column, String value) {

		registers[row][column] = value;
		System.out.println("New Value : " + registers[row][column]);
	}

}
