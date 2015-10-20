package Model;

public class Instruction {

	private int lineNumber;
	private String line;
	private String command;
	private String rd;
	private String rs;
	private String rt;
	private String imm;
	private String shift;
	private String label;
	private String jumpLink;
	private String comment;
	private String opcode;

	public Instruction(int lineNumber, String line, String command, String rd, String rs, String rt, String imm,
			String shift, String label, String jumpLink, String comment) {
		super();
		this.lineNumber = lineNumber;
		this.line = line;
		this.command = command;
		this.rd = rd;
		this.rs = rs;
		this.rt = rt;
		this.imm = imm;
		this.shift = shift;
		this.label = label;
		this.jumpLink = jumpLink;
		this.comment = comment;
	}

	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getRd() {
		return rd;
	}

	public void setRd(String rd) {
		this.rd = rd;
	}

	public String getRs() {
		return rs;
	}

	public void setRs(String rs) {
		this.rs = rs;
	}

	public String getRt() {
		return rt;
	}

	public void setRt(String rt) {
		this.rt = rt;
	}

	public String getImm() {
		return imm;
	}

	public void setImm(String imm) {
		this.imm = imm;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getJumpLink() {
		return jumpLink;
	}

	public void setJumpLink(String jumpLink) {
		this.jumpLink = jumpLink;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
