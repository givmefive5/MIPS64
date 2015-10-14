package Model;

import java.util.HashMap;

public class InstructionFormat {
	static HashMap<String, String> insMap = new HashMap<String, String>();
	static InstructionFormat instance = null;
	
	public static InstructionFormat getInstance(){
		if(instance == null){
			initializeMap();
			instance =  new InstructionFormat();
		}
		return instance;
	}
	
	private static void initializeMap(){
		
		//R-Type
		insMap.put("DADDU", "000000");
		insMap.put("DMULT", "000000");	
		insMap.put("OR", "000000");		
		insMap.put("SLT", "000000");	

		//I-Type
		insMap.put("BEQ", "000100");
		insMap.put("LW", "100011");		
		insMap.put("LWU", "100111");	
		insMap.put("SW", "101011");		
		insMap.put("DSLL", "00000000000"); //With 5 RS 0's
		insMap.put("ANDI", "001100");
		insMap.put("DADDIU", "011001");

		//J-Type
		insMap.put("J", "000010");
		
		//Floating-Point
		insMap.put("L.S", "110001");
		insMap.put("S.S", "111001");
		insMap.put("ADD.S", "01000110000"); //With 5 RS 0's
		insMap.put("MUL.S", "01000110000"); //With 5 RS 0's
		
	}
}
