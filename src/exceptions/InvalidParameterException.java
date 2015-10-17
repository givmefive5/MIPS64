package exceptions;

public class InvalidParameterException extends Exception {

	public InvalidParameterException() {
		super("Unparseable parameters.");
	}
}
