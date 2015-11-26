package service;

import java.math.BigInteger;

public class FloatingPointConverter {

	public static Float convertHexToFloat(String hex) {
		BigInteger i = new BigInteger(hex, 16);
		return Float.intBitsToFloat(i.intValue());
	}

	public static String convertFloatToHex(Float num) {
		System.out.println(hex(num).substring(2));
		return hex(num).substring(2);
	}

	public static String hex(int n) {
		// call toUpperCase() if that's required
		return String.format("0x%8s", Integer.toHexString(n)).replace(' ', '0');
	}

	public static String hex(float f) {
		// change the float to raw integer bits(according to the OP's
		// requirement)
		return hex(Float.floatToRawIntBits(f));
	}
}
