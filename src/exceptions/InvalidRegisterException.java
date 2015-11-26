package exceptions;

public class InvalidRegisterException extends Exception {

	String[] invalidRegisters;

	public InvalidRegisterException(String[] invalidRegisters) {
		super("Invalid Register/s");
		this.invalidRegisters = invalidRegisters;
	}

	public String[] getInvalidRegisters() {
		return invalidRegisters;
	}
}
