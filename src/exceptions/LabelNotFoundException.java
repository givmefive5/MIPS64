package exceptions;

public class LabelNotFoundException extends Exception {

	String line;
	int lineNumber;

	public LabelNotFoundException(String label, String line, int lineNumber) {
		super("Label: " + label + " not found!");
		this.line = line;
		this.lineNumber = lineNumber;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
}
