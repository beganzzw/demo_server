package org.gwhere.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * 生成SHA-1/MD5消息摘要的工具类
 * 
 * @author dzl
 */
public class Digests {

	private static final String SHA1 = "SHA-1";
	private static final String MD5 = "MD5";
	
	/**
	 * 对byte数组进行SHA-1散列
	 * 
	 * @param input
	 * @return
	 */
	public static byte[] sha1(byte[] input) {
		return digest(input, SHA1, null, 1);
	}

	/**
	 * 对byte数组进行SHA-1散列
	 * 
	 * @param input
	 * @param salt
	 * @return
	 */
	public static byte[] sha1(byte[] input, byte[] salt) {
		return digest(input, SHA1, salt, 1);
	}

	/**
	 * 对byte数组进行SHA-1散列
	 * 
	 * @param input
	 * @param salt
	 * @param iterations
	 * @return
	 */
	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA1, salt, iterations);
	}

	/**
	 * 为byte数组生成数据摘要
	 * 
	 * @param input
	 * @param algorithm
	 * @param salt
	 * @param iterations
	 * @return
	 */
	private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对输入流进行MD5散列
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] md5(InputStream input) throws IOException {
		return digest(input, MD5);
	}

	/**
	 * 对输入流进行SHA-1散列
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] sha1(InputStream input) throws IOException {
		return digest(input, SHA1);
	}

	/**
	 * 为输入流生成消息摘要
	 * 
	 * @param input
	 * @param algorithm
	 * @return              
	 * @throws IOException
	 */
	private static byte[] digest(InputStream input, String algorithm) throws IOException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			int bufferLength = 8 * 1024;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);

			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}

			return messageDigest.digest();
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
