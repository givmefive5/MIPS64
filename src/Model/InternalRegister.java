package Model;

public class InternalRegister {

	String[] values;

	public InternalRegister() {
		values = new String[16];
	}

	public String[] getValues() {
		return values;
	}

	public String getIFIDIR() {
		return values[0];
	}

	public void setIFIDIR(String value) {
		values[0] = value;
	}

	public String getIFIDNPC() {
		return values[1];
	}

	public void setIFIDNPC(String value) {
		values[1] = value;
	}

	public String getPC() {
		return values[2];
	}

	public void setPC(String value) {
		values[2] = value;
	}

	public String getIDEXA() {
		return values[3];
	}

	public void setIDEXA(String value) {
		values[3] = value;
	}

	public String getIDEXB() {
		return values[4];
	}

	public void setIDEXB(String value) {
		values[4] = value;
	}

	public String getIDEXIMM() {
		return values[5];
	}

	public void setIDEXIMM(String value) {
		values[5] = value;
	}

	public String getIDEXIR() {
		return values[6];
	}

	public void setIDEXIR(String value) {
		values[6] = value;
	}

	public String getIDEXNPC() {
		return values[7];
	}

	public void setIDEXNPC(String value) {
		values[7] = value;
	}

	public void setEXMEMALUOutput(String value) {
		values[8] = value;
	}

	public String getEXMEMALUOutput() {
		return values[8];
	}

	public void setEXMEMCond(String value) {
		values[9] = value;
	}

	public String getEXMEMIR() {
		return values[10];
	}

	public void setEXMEMIR(String value) {
		values[10] = value;
	}

	public String getEXMEMB() {
		return values[11];
	}

	public void setEXMEMB(String value) {
		values[11] = value;
	}

	public String getMEMWBLMD() {
		return values[12];
	}

	public void setMEMWBLMD(String value) {
		values[12] = value;
	}

	public String getMEMWBIR() {
		return values[13];
	}

	public void setMEMWBIR(String value) {
		values[13] = value;
	}

	public String getMEMWBALUOutput() {
		return values[14];
	}

	public void setMEMWBALUOutput(String value) {
		values[14] = value;
	}

	public String getRn() {
		return values[15];
	}

	public void setRn(String value) {
		values[15] = value;
	}
}
