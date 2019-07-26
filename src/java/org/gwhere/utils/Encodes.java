package org.gwhere.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 编码解码工具
 * 
 * @author dzl
 *
 */
public class Encodes {

	/**
	 * Hex编码
	 * @param input
	 * @return
	 */
	public static String encodeHex(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Base64编码
	 * 
	 * @param input
	 * @return
	 */
	public static String encodeBase64(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	/**
	 * Base64解码
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] decodeBase64(String input) {
		return Base64.decodeBase64(input);
	}
	
	/**
	 * 包装unchecked异常
	 * 
	 * @param ex
	 * @return
	 */
	public RuntimeException unchecked(Throwable ex) {
		if (ex instanceof RuntimeException) {
			return (RuntimeException) ex;
		} else {
			return new RuntimeException(ex);
		}
	}
}
