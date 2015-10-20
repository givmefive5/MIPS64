package Model;

public class Error {

	String line;
	int lineNumber;
	String errorMessage;

	public Error(String line, int lineNumber, String errorMessage) {
		super();
		this.line = line;
		this.errorMessage = errorMessage;
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
