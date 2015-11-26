package exceptions;

public class UnrecognizedCommandException extends Exception {

	public UnrecognizedCommandException() {
		super("Command not found");
	}
}
