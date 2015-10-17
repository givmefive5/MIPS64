package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.Command;
import exceptions.InvalidFormatException;

public class MIPS64Checker {
	
	public static void printCommands(ArrayList<Command> com){
		for(Command Command: com){
			System.out.print(Command.getFuncName() + " " + Command.getInstruction() + " ");
			for(String s: Command.getRegisters())
				System.out.print(s + " ");
			System.out.println();
		}
	}

	public static List<Command> parse(String text) throws InvalidFormatException {
		
		ArrayList<Command> Commands = new ArrayList<Command>();

		String sFullcode[] = text.split("\\r?\\n");
		
		for(String s: sFullcode){
			Scanner scanner = new Scanner(s);
			
			if(scanner.hasNext()){
				String firstArg = scanner.next();
				String label = null;
				String instruction;
				String[] registers;
				
				if(firstArg.contains(":")){
					label = firstArg;	
					instruction = scanner.next();
				}
				
				else instruction = firstArg;
				
				registers = scanner.nextLine().trim().split(",");
				
				Commands.add(new Command(label, instruction, registers));
	
				scanner.close();
			}
		}

		printCommands(Commands);
		return Commands;
		
		//throw new InvalidFormatException("Parsing error, unknown format");
	}
}
