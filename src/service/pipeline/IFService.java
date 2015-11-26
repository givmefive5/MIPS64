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

	int prevLineNumber = -1;

	public IFService(InternalRegister ir, RevisedPipelineService pipelineService) {
		super(ir, pipelineService);
	}

	@Override
	public void run(int cycleNumber) {
		try {
			// Fix such tht IF of instruction 1 should stall when ID of
			// instruction 0 has not yet executed.
			// Possible solution would be to lock IF/ID.IR in IF and release in
			// ID.
			Instruction peek = queue.peek();

			Instruction idPeek = pipelineService.peekAtIDService();

			if (idPeek != null && idPeek.getLineNumber() == prevLineNumber) {
				System.out.println("ID STILL TO EXECUTE FIRST");
			} else if (peek != null) {
				if ((peek.isIdFinished() == true && peek.isWbFinished() == false) || (peek.isIdFinished()
						&& peek.isWbFinished() && peek.getWbFinishedAtCycleNumber() == cycleNumber)) {
					System.out.println("Need to wait");
				} else {
					Instruction ins = queue.remove();
					PipelineMapController.setMapValue("IF", ins.getLineNumber(), cycleNumber);

					String opcode = ir.getIFIDIR();
					String binary = null;
					if (opcode != null) {
						binary = BinaryHexConverter.convertHexToBinary(opcode, 32);
					}
					if (binary != null && binary.substring(0, 6).equals("000100")
							&& isEqualRegisters(binary.substring(6, 11), binary.substring(11, 16))) { // BEQ
						int npc;
						if (ir.getIFIDNPC() != null) {
							npc = Integer.parseInt(ir.getIFIDNPC(), 16);
						} else {
							npc = 0;
						}

						String immediate = StringUtils.leftPad(binary.substring(16), 32, binary.substring(16, 17));
						long l = Long.parseLong(immediate, 2);
						int imm = (int) l * 4;
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
						pipelineService.addInstructionTo("ID", ins.getLineNumber());
					}
					ir.setIFIDIR(ins.getHexOpcode());

					prevLineNumber = ins.getLineNumber();
					int nextInsLineNumber = Integer.parseInt(ir.getIFIDNPC(), 16) / 4;
					pipelineService.addInstructionTo("IF", nextInsLineNumber);
					ins.resetPipelineFlags();
					ins.setIfFinished(true);
				}
			}
		} catch (NoSuchElementException e) {
		}

	}

	private void addRemainingFunctionsToQueue(int instructionNumber) {

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
