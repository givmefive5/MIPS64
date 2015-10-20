package exceptions;

public class InvalidRegisterValueException extends Exception {

	public InvalidRegisterValueException(String value) {
		super(value + " is not a valid register value");
	}
}
