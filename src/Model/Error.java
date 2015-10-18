package Model;

public class Error {

	String code;
	int lineNumber;
	String errorMessage;

	public Error(String code, int lineNumber, String errorMessage) {
		super();
		this.code = code;
		this.lineNumber = lineNumber;
		this.errorMessage = errorMessage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
