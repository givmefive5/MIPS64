package service.pipeline;

import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;
import controller.RegistersController;
import service.BinaryHexConverter;
import service.RevisedPipelineService;

public class IFService extends PipelineFunction {

	RevisedPipelineService pipelineService;

	public IFService(InternalRegister ir, RevisedPipelineService pipelineService) {
		super(ir);
		this.pipelineService = pipelineService;
	}

	@Override
	public void run(int cycleNumber) {
		try {
			Instruction ins = queue.remove();
			PipelineMapController.setMapValue("IF", ins.getLineNumber(), cycleNumber);

			String opcode = ir.getIFIDIR();
			String binary = null;
			if (opcode != null) {
				binary = BinaryHexConverter.convertHexToBinary(opcode);
			}
			if (binary != null && binary.substring(0, 6).equals("000100")
					&& isEqualRegisters(binary.substring(6, 11), binary.substring(11, 16))) { // BEQ
				int npc;
				if (ir.getIFIDNPC() != null) {
					npc = Integer.parseInt(ir.getIFIDNPC(), 16);
				} else {
					npc = 0;
				}
				int imm = Integer.parseInt(binary.substring(16), 2) * 4;
				String hex = StringUtils.leftPad(Integer.toHexString(npc + imm), 16, "0").toUpperCase();
				ir.setIFIDNPC(hex);
				ir.setPC(hex);
			} else if (binary != null && binary.substring(0, 6).equals("000010")) { // J
				String address = binary.substring(8) + "00";
				String hex = BinaryHexConverter.convertBinaryToHex(address, 16).toUpperCase();
				ir.setIFIDNPC(hex);
				ir.setPC(hex);
			} else {
				String npc = StringUtils.leftPad(Integer.toHexString((ins.getLineNumber() + 1) * 4), 16, "0")
						.toUpperCase();
				ir.setIFIDNPC(npc);
				ir.setPC(npc);
				System.out.println("Added 4 Remaining for instruction : " + ins.getCommand());
				addRemainingFunctionsToQueue(ins.getLineNumber());
			}
			ir.setIFIDIR(ins.getHexOpcode());

			int instructionNumber = Integer.parseInt(ir.getIFIDNPC(), 16) / 4;
			pipelineService.addInstructionTo("IF", instructionNumber);
			ins.resetPipelineFlags();
			ins.setIfFinished(true);
		} catch (NoSuchElementException e) {
		}

	}

	private void addRemainingFunctionsToQueue(int instructionNumber) {
		pipelineService.addInstructionTo("ID", instructionNumber);
		pipelineService.addInstructionTo("EX", instructionNumber);
		pipelineService.addInstructionTo("MEM", instructionNumber);
		pipelineService.addInstructionTo("WB", instructionNumber);
	}

	private boolean isEqualRegisters(String regBin1, String regBin2) {
		// for BEQ
		if (regBin1 == null || regBin2 == null)
			return false;
		else {
			int index1 = Integer.parseInt(regBin1, 2);
			int index2 = Integer.parseInt(regBin2, 2);
			String val1 = RegistersController.getInstance().getValue(index1, 1);
			String val2 = RegistersController.getInstance().getValue(index2, 1);
			if (val1.equals(val2))
				return true;
			else
				return false;
		}

	}
}
