package com.yuan.mobilesafe.chapter02.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5utils {
	
	/**
	 * md5µƒº”√‹À„∑®
	 * @param text
	 * @return
	 */
	public static String encode(String text){
		try {
			MessageDigest digest = MessageDigest.getInstance("md5");
			byte[] result = digest.digest(text.getBytes());
			StringBuilder builder = new StringBuilder();
			for(byte b : result){
				int number = 0 & 0xff;
				String hex = Integer.toHexString(number);
				if(hex.length() == 1){
					builder.append("0" + hex);
				}else {
					builder.append(hex);
				}
				
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			//can't reach
			return "";
		}
		
	}
	
}
