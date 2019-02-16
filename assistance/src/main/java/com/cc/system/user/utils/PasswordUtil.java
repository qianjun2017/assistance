/**
 * 
 */
package com.cc.system.user.utils;

import java.util.Random;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author Administrator
 *
 */
public class PasswordUtil {
	
	private static final String salts = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String getSalt(int lenth){
		if (lenth<1) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		int i=0;
		while (i<lenth) {
			int nextInt = random.nextInt(salts.length());
			buffer.append(salts.charAt(nextInt));
			i++;
		}
		return buffer.substring(0);
	}
	
	/**
	 * 加密 md5
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String encrypt(String password, String salt){
		ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
		SimpleHash simpleHash = new SimpleHash("md5", password, credentialsSalt, 2);
		return simpleHash.toHex();
	}
}
