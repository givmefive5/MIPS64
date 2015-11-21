package service.pipeline;

import java.math.BigInteger;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;

import Model.Instruction;
import Model.InternalRegister;
import controller.PipelineMapController;
import controller.RegistersController;
import service.BinaryHexConverter;
import service.RevisedPipelineService;

public class IDService extends PipelineFunction {

	RegistersController registersController = RegistersController.getInstance();

	public IDService(InternalRegister ir, RevisedPipelineService pipelineService) {
		super(ir, pipelineService);

	}

	@Override
	public void run(int cycleNumber) {
		try {
			Instruction peek = queue.peek();
			if (peek != null
					&& (registersController.isLocked(peek.getRs()) || registersController.isLocked(peek.getRt())
							|| registersController.getCycleNumberReleased(peek.getRs()) == cycleNumber
							|| registersController.getCycleNumberReleased(peek.getRt()) == cycleNumber)) {

			} else if (peek != null && peek.isIfFinished()) {
				Instruction ins = queue.remove();

				String command = ins.getCommand().toUpperCase();
				BigInteger integer = new BigInteger(ins.getHexOpcode(), 16);
				String binary = StringUtils.leftPad(integer.toString(2), 32, "0");

				String imm, padChar;

				imm = binary.substring(16, 32);
				padChar = imm.substring(0, 1);
				imm = StringUtils.leftPad(imm, 64, padChar);
				imm = BinaryHexConverter.convertBinaryToHex(imm, 16);

				int regA = Integer.parseInt(binary.substring(6, 11), 2);
				int regB = Integer.parseInt(binary.substring(11, 16), 2);

				String a, b;

				if (command.equals("L.S") || command.equals("S.S")) {
					a = RegistersController.getInstance().getValue(regA, 1);
					b = RegistersController.getInstance().getValue(regB, 3);
					ir.setIDEXA(StringUtils.leftPad(a, 16, "0"));
					ir.setIDEXB(b);
				} else if (command.equals("ADD.S") || command.equals("MUL.S")) {
					regA = Integer.parseInt(binary.substring(11, 16), 2);
					regB = Integer.parseInt(binary.substring(16, 21), 2);
					a = RegistersController.getInstance().getValue(regA, 3);
					b = RegistersController.getInstance().getValue(regB, 3);
					ir.setIDEXA(StringUtils.leftPad(a, 16, "0"));
					ir.setIDEXB(StringUtils.leftPad(b, 16, "0"));
				} else if (command.equals("DSLL")) {
					regA = Integer.parseInt(binary.substring(11, 16), 2);
					regB = Integer.parseInt(binary.substring(16, 21), 2);
					a = RegistersController.getInstance().getValue(regA, 1);
					b = RegistersController.getInstance().getValue(regB, 1);
					ir.setIDEXA(StringUtils.leftPad(a, 16, "0"));
					ir.setIDEXB(StringUtils.leftPad(b, 16, "0"));
				} else {
					a = RegistersController.getInstance().getValue(regA, 1);
					b = RegistersController.getInstance().getValue(regB, 1);
					ir.setIDEXA(StringUtils.leftPad(a, 16, "0"));
					ir.setIDEXB(StringUtils.leftPad(b, 16, "0"));
				}
				ir.setIDEXIR(ins.getHexOpcode());
				ir.setIDEXIMM(imm.toUpperCase());
				ir.setIDEXNPC(ir.getIFIDNPC());

				PipelineMapController.setMapValue("ID", ins.getLineNumber(), cycleNumber);
				ins.setIdFinished(true);
				pipelineService.addInstructionTo("EX", ins.getLineNumber());
				registersController.lock(ins.getRd());
			}
		} catch (NoSuchElementException e) {

		}
	}

}
