package com.armorhero.util.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class TripleDesWrapperUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(TripleDesWrapperUtil.class);
	private static TripleDesWrapperUtil instance = null;

	private static Cipher ciperEnc;
	private static Cipher ciperDec;
	private static Key key_;
	static byte[]  keys = { 0x03, 0x03, 0x03, 0x03, 0x03, 0x03, 0x03, 0x03, 0x02, 0x02,
	        0x02, 0x02, 0x02, 0x02, 0x02, 0x02, 0x01, 0x01, 0x01, 0x01, 0x01,
	        0x01, 0x01, 0x01 };

//	private TripleDesWrapper() {
//		try {
//			ciper_ = Cipher.getInstance("TRIPLEDES/ecb/NoPadding");
//			key_ = new SecretKeySpec(keys, "TripleDES");
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			//e.printStackTrace();
//		}
//	}

	static {
	        try {
	            key_ = new SecretKeySpec(keys, "TripleDES");
	            ciperEnc = Cipher.getInstance("TRIPLEDES/ecb/NoPadding");
	            ciperEnc.init(Cipher.ENCRYPT_MODE, key_);
	            ciperDec = Cipher.getInstance("TRIPLEDES/ecb/NoPadding");
	            ciperDec.init(Cipher.DECRYPT_MODE, key_);
	        } catch (Exception e) {
	        	LOGGER.error(e.getMessage(), e);
	            //e.printStackTrace();
	        }
	    }
	 
	public static TripleDesWrapperUtil getInstance() {
		if (instance == null)
			instance = new TripleDesWrapperUtil();
		return instance;
	}

	public final String doEnc(String strPlain) {

		byte encrypted[] = { 0 };
		try {
			byte input[] = strPlain.getBytes();
			input = FoxPadding(input);
			synchronized (ciperEnc) {
				// 并发情况下 init防冲突 
				ciperEnc.init(Cipher.ENCRYPT_MODE, key_);
				encrypted = ciperEnc.doFinal(input);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			//e.printStackTrace();
		}
		return byte2String(encrypted);
	}

	public final String doDec(String strEnc) {
		String strDec = "";
		byte[] encbytes = hex2Byte(strEnc);
		try {
			synchronized (ciperDec) {
				// 并发情况下 init防冲突 
				ciperDec.init(Cipher.DECRYPT_MODE, key_);
				byte output[] = ciperDec.doFinal(encbytes);
				output = FoxUnPadding(output);
				strDec = new String(output);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return strDec;
	}

	public byte[] hex2Byte(String hex) throws IllegalArgumentException {
		if (hex.length() % 2 != 0)
			throw new IllegalArgumentException();
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}

	private String byte2String(byte[] bt) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bt.length; i++) {
			sb.append(byte2Hex(bt[i]));
		}
		return sb.toString();
	}

	private String byte2Hex(byte ib) {
		/* if want to output normal letter please user DigitNormal */
		char[] DigitNormal = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
		        '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] ob = new char[2];
		ob[0] = DigitNormal[(ib >>> 4) & 0X0F];
		ob[1] = DigitNormal[ib & 0X0F];
		String s = new String(ob);
		return s;
	}

	private byte[] FoxPadding(byte[] input) {
		int len = input.length;
		int timesOf8 = len / 8;
		int residual = len % 8;

		if (residual != 0) {
			timesOf8++;
		}
		int lenret = 8 * timesOf8;
		byte[] ret = new byte[lenret];
		int i = 0;
		for (i = 0; i < len; i++) {
			ret[i] = input[i];
		}
		for (i = len; i < lenret; i++) {
			ret[i] = 0;
		}
		return ret;
	}

	private byte[] FoxUnPadding(byte[] input) {
		int len = input.length;
		int lenret = len - 1;
		if (len>0) {
			while (input[lenret] == 0) {
				lenret--;
			}
		}
		lenret = lenret + 1;
		byte[] ret = new byte[lenret];
		for (int i = 0; i < lenret; i++) {
			ret[i] = input[i];
		}
		return ret;
	}

	public static void main(String[] args) {
		String o1 = "Innoplay2014@#$";
		System.out.println("未加密：" + o1);
		String o2 = TripleDesWrapperUtil.getInstance().doEnc(o1);
		System.out.println("加密: " + o2);
		String oo = TripleDesWrapperUtil.getInstance().doDec("e85e4ebb69b0d27853804124cf795d02");
		System.out.println("解密: " + oo);
		//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//		Date date = new Date(System.currentTimeMillis());
		//		System.out.println(sdf.format(date));
	}
	//	/**
	//	public static void  main(String[] args){
	//		String o1 = "12345666278######";
	//		int length = o1.length();
	//		int split = length - 6;
	//		String fixedpasswd = o1.substring(0, split);
	//		System.out.println("fixed: " + fixedpasswd);
	//		String dynpasswd = o1.substring(split);
	//		System.out.println("dyn: " + dynpasswd);
	//	}	
	//	*/
}
