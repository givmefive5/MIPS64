package service;

import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

public class BinaryHexConverter {

	public static String convertBinaryToHex(String binary, int length) {
		BigInteger integer = new BigInteger(binary, 2);
		String hex = integer.toString(16);
		return StringUtils.leftPad(hex, length, "0");
	}

}
