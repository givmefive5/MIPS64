package service;

import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

public class BinaryHexConverter {

	public static String convertBinaryToHex(String binary, int length) {
		BigInteger integer = new BigInteger(binary, 2);
		String hex = integer.toString(16);
		return StringUtils.leftPad(hex, length, "0");
	}

	public static String convertHexToBinary(String hex, int length) {
		BigInteger integer = new BigInteger(hex, 16);
		String bin = StringUtils.leftPad(integer.toString(2), length, "0");
		return bin;
	}

}
