package service.pipeline;

import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;
import Model.InternalRegister;
import controller.MemoryController;
import controller.PipelineMapController;
import service.BinaryHexConverter;
import service.FloatingPointConverter;
import service.RevisedPipelineService;

public class MEMService extends PipelineFunction {
	public MEMService(InternalRegister ir, RevisedPipelineService pipelineService) {
		super(ir, pipelineService);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			Instruction peek = queue.peekFirst();
			if (peek != null && isExFinished(peek)) {
				Instruction ins = queue.remove();

				String command = ins.getCommand().toUpperCase();

				ir.setMEMWBIR(ir.getEXMEMIR());
				if (command.equals("MUL.S")) {
					ir.setMEMWBALUOutput(ir.getEXMEMALUOutputMULS());
				} else if (command.equals("ADD.S")) {
					ir.setMEMWBALUOutput(ir.getEXMEMALUOutputADDS());
				} else {
					ir.setMEMWBALUOutput(ir.getEXMEMALUOutput());
					if (command.equals("LW") || command.equals("LWU")) {
						String memHex = MemoryController.getHexWordFromMemory(ir.getEXMEMALUOutput());
						String memValBin = BinaryHexConverter.convertHexToBinary(memHex, 32);
						String padChar = "0";
						if (command.equals("LW"))
							padChar = memValBin.substring(0, 1);
						memValBin = StringUtils.leftPad(memValBin, 64, padChar);
						String hex = BinaryHexConverter.convertBinaryToHex(memValBin, 16).toUpperCase();
						ir.setMEMWBLMD(hex);
					} else if (command.equals("L.S")) {
						String memoryValueHex = MemoryController.getHexWordFromMemory(ir.getEXMEMALUOutput());
						Float floatNum = FloatingPointConverter.convertHexToFloat(memoryValueHex);
						ir.setMEMWBLMD(floatNum.toString());
					} else if (command.equals("SW")) {
						String word = ir.getEXMEMB().substring(8, 16);
						MemoryController.storeWordToMemory(word, ir.getEXMEMALUOutput());
					} else if (command.equals("S.S")) {
						String floatString = ir.getEXMEMB();
						String hex = FloatingPointConverter.convertFloatToHex(Float.parseFloat(floatString));
						MemoryController.storeWordToMemory(hex, ir.getEXMEMALUOutput());
					}
				}
				PipelineMapController.setMapValue("MEM", ins.getLineNumber(), cycleNumber);
				ins.setMemFinished(true);
			}

		} catch (NoSuchElementException e) {

		}
	}

	private boolean isExFinished(Instruction peek) {
		if (peek.getCommand().equals("MUL.S") && peek.getExFinished() == 6)
			return true;
		else if (peek.getCommand().equals("ADD.S") && peek.getExFinished() == 4)
			return true;
		else if (!peek.getCommand().equals("MUL.S") && !peek.getCommand().equals("ADD.S") && peek.getExFinished() == 1)
			return true;
		else
			return false;

	}
}
