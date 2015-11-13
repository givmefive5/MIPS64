package Model;

public class Register {

	private String value;
	private boolean isLocked;

	public Register(String value) {
		this.value = value;
		isLocked = true;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

}
