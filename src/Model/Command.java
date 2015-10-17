package Model;

public class Command {
	private String funcName;
	private String instruction;
	private String[] registers;
	
	public Command(String funcName, String instruction, String[] registers){
		this.funcName = funcName;
		this.instruction = instruction;
		this.registers = registers;
	}
	
	public String getFuncName() {
		return funcName;
	}

	public String getInstruction() {
		return instruction;
	}

	public String[] getRegisters() {
		return registers;
	}
}
