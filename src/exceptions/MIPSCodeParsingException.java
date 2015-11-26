package exceptions;

import java.util.ArrayList;
import java.util.List;

import Model.Error;

public class MIPSCodeParsingException extends Exception {

	List<Error> errors = new ArrayList<>();

	public MIPSCodeParsingException(List<Error> errors) {
		super("Errors found: ");
		this.errors = errors;
	}

	public List<Error> getErrors() {
		return errors;
	}

}
