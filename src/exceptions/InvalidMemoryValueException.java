package exceptions;

public class InvalidMemoryValueException extends Exception {

	public InvalidMemoryValueException(String value) {
		super(value + " is not a valid memory value");
	}
}
