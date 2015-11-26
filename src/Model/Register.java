package Model;

public class Register {

	private String value;
	private boolean isLocked;
	private int cycleNumberReleased;

	public Register(String value) {
		this.value = value;
		isLocked = false;
		cycleNumberReleased = -1;
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

	public int getCycleNumberReleased() {
		return cycleNumberReleased;
	}

	public void setCycleNumberReleased(int cycleNumberReleased) {
		this.cycleNumberReleased = cycleNumberReleased;
	}

}
