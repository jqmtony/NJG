package com.kingdee.eas.bpmdemo.JBrowserHelper;

import java.security.MessageDigest;

public class MD5Helper {

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println("呵呵:-------->"+MD5Helper.MD5("blue20140813K2"));
	}
	
	/**
	 * 
	 * @param userName 用户名称 如blue
	 * @param date  日期 格式化为yyyymmdd
	 * @param type  系统类型 如 K2
	 * @return
	 */
	public static String getMd5(String userName,String date,String type)
	{
		return MD5Helper.MD5(userName+date+type);
	}
}
