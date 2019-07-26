package org.gwhere.utils;

import java.io.UnsupportedEncodingException;

public class PasswordHelper {

	private final static Integer ITERATIONS = 2;
	
	public static String generatePassword(String source, String salt){
		return generatePassword(source, salt, ITERATIONS);
	}
	
	public static String generatePassword(String source, String salt, int iterations) {
		return Encodes.encodeHex(Digests.sha1(source.getBytes(), salt.getBytes(), iterations));
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
//		System.out.println(StringUtils.generateUUID());
//		System.out.println(MD5.getMD5("source=wx_crm&openid=obnX8vv_WSbrydEYK-6yaL_0rTy4&timestamp=1546418156&key=dianxin").toLowerCase());
		System.out.println(generatePassword("zheshimima", "admin"));
	}
}
