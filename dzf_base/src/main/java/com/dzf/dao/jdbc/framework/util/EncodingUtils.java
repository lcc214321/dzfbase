package com.dzf.dao.jdbc.framework.util;

import sun.misc.BASE64Encoder;

public final class EncodingUtils {

	/**
	 * 
	 */
	private static BASE64Encoder encoder;

	/**
	 * 
	 * @param is
	 */
	public static String base64Encode(byte[] bytes) {
		if (encoder == null) {
			synchronized (EncodingUtils.class) {
				if (encoder == null) {
					encoder = new BASE64Encoder();
				}
			}
		}
		return encoder.encode(bytes);
	}
}