package Model;

public class InternalRegister {

	String[][] irs;

	public InternalRegister() {
		irs = new String[24][2];
		irs[0][0] = "IF/ID.IR";
		irs[1][0] = "IF/ID.NPC";
		irs[2][0] = "PC";
		irs[3][0] = "ID/EX.A";
		irs[4][0] = "ID/EX.B";
		irs[5][0] = "ID/EX.IMM";
		irs[6][0] = "ID/EX.IR";
		irs[7][0] = "ID/EX.NPC";
		irs[8][0] = "EX/MEM.ALUOutput";
		irs[9][0] = "EX/MEM.COND";
		irs[10][0] = "EX/MEM.IR";
		irs[11][0] = "EX/MEM.B";
		irs[12][0] = "EX/MEM.ALUOutput.MULS";
		irs[13][0] = "EX/MEM.COND.MULS";
		irs[14][0] = "EX/MEM.IR.MULS";
		irs[15][0] = "EX/MEM.B.MULS";
		irs[16][0] = "EX/MEM.ALUOutput.ADDS";
		irs[17][0] = "EX/MEM.COND.ADDS";
		irs[18][0] = "EX/MEM.IR.ADDS";
		irs[19][0] = "EX/MEM.B.ADDS";
		irs[20][0] = "MEM/WB.LMD";
		irs[21][0] = "MEM/WB.IR";
		irs[22][0] = "MEM/WB.ALUOutput";
		irs[23][0] = "Rn";
		for (int i = 0; i < irs.length; i++) {
			irs[i][1] = null;
		}
	}

	public String[][] getIRs() {
		return irs;
	}

	public String getIFIDIR() {
		return irs[0][1];
	}

	public void setIFIDIR(String value) {
		irs[0][1] = value;
	}

	public String getIFIDNPC() {
		return irs[1][1];
	}

	public void setIFIDNPC(String value) {
		irs[1][1] = value;
	}

	public String getPC() {
		return irs[2][1];
	}

	public void setPC(String value) {
		irs[2][1] = value;
	}

	public String getIDEXA() {
		return irs[3][1];
	}

	public void setIDEXA(String value) {
		irs[3][1] = value;
	}

	public String getIDEXB() {
		return irs[4][1];
	}

	public void setIDEXB(String value) {
		irs[4][1] = value;
	}

	public String getIDEXIMM() {
		return irs[5][1];
	}

	public void setIDEXIMM(String value) {
		irs[5][1] = value;
	}

	public String getIDEXIR() {
		return irs[6][1];
	}

	public void setIDEXIR(String value) {
		irs[6][1] = value;
	}

	public String getIDEXNPC() {
		return irs[7][1];
	}

	public void setIDEXNPC(String value) {
		irs[7][1] = value;
	}

	public void setEXMEMALUOutput(String value) {
		irs[8][1] = value;
	}

	public String getEXMEMALUOutput() {
		return irs[8][1];
	}

	public String getEXMEMCond() {
		return irs[9][1];
	}

	public void setEXMEMCond(String value) {
		irs[9][1] = value;
	}

	public String getEXMEMIR() {
		return irs[10][1];
	}

	public void setEXMEMIR(String value) {
		irs[10][1] = value;
	}

	public String getEXMEMB() {
		return irs[11][1];
	}

	public void setEXMEMB(String value) {
		irs[11][1] = value;
	}

	public void setEXMEMALUOutputMULS(String value) {
		irs[12][1] = value;
	}

	public String getEXMEMALUOutputMULS() {
		return irs[12][1];
	}

	public String getEXMEMCondMULS() {
		return irs[13][1];
	}

	public void setEXMEMCondMULS(String value) {
		irs[13][1] = value;
	}

	public String getEXMEMIRMULS() {
		return irs[14][1];
	}

	public void setEXMEMIRMULS(String value) {
		irs[14][1] = value;
	}

	public String getEXMEMBMULS() {
		return irs[15][1];
	}

	public void setEXMEMBMULS(String value) {
		irs[15][1] = value;
	}

	public void setEXMEMALUOutputADDS(String value) {
		irs[16][1] = value;
	}

	public String getEXMEMALUOutputADDS() {
		return irs[16][1];
	}

	public String getEXMEMCondADDS() {
		return irs[17][1];
	}

	public void setEXMEMCondADDS(String value) {
		irs[17][1] = value;
	}

	public String getEXMEMIRADDS() {
		return irs[18][1];
	}

	public void setEXMEMIRADDS(String value) {
		irs[18][1] = value;
	}

	public String getEXMEMBADDS() {
		return irs[19][1];
	}

	public void setEXMEMBADDS(String value) {
		irs[19][1] = value;
	}

	public String getMEMWBLMD() {
		return irs[20][1];
	}

	public void setMEMWBLMD(String value) {
		irs[20][1] = value;
	}

	public String getMEMWBIR() {
		return irs[21][1];
	}

	public void setMEMWBIR(String value) {
		irs[21][1] = value;
	}

	public String getMEMWBALUOutput() {
		return irs[22][1];
	}

	public void setMEMWBALUOutput(String value) {
		irs[22][1] = value;
	}

	public String getRn() {
		return irs[23][1];
	}

	public void setRn(String value) {
		irs[23][1] = value;
	}
}
