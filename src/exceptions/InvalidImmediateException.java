package exceptions;

public class InvalidImmediateException extends Exception {

	String line;
	int lineNumber;

	public InvalidImmediateException(String imm, String line, int lineNumber) {
		super("Immediate: " + imm + " is invalid!");
		this.line = line;
		this.lineNumber = lineNumber;
	}

	public String getLine() {
		return line;
	}

	public void setCommand(String line) {
		this.line = line;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}
