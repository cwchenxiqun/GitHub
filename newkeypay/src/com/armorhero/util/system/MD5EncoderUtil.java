package com.armorhero.util.system;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

@SuppressWarnings("restriction")
public class MD5EncoderUtil {
	private static Object locker = new Object();
	private static MessageDigest mDigest = null;
	private static MD5EncoderUtil instance = null;

	public synchronized static MD5EncoderUtil getInstance() throws Exception {
		if (instance == null)
			instance = new MD5EncoderUtil();
		return instance;
	}

	private MD5EncoderUtil() throws Exception {
		mDigest = MessageDigest.getInstance("MD5");
	}

	public String md5Encode(String s) throws Exception {
		synchronized (locker) {
			byte strTemp[] = s.getBytes();
			mDigest.update(strTemp);
			byte md[] = mDigest.digest();
			return (new BASE64Encoder()).encode(md);
		}
	}

	public static void main(String[] args) {
		System.out.println("MD5 加密算法...");
		try {
			System.out.println("md5_base64: "
			        + MD5EncoderUtil.getInstance().md5Encode(
			                "111111"));
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
	}
}
