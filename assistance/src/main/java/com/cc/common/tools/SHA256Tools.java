/**
 * 
 */
package com.cc.common.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.cc.common.exception.LogicException;

/**
 * @author Administrator
 *
 */
public class SHA256Tools {

	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5','6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	
	private static String getFormattedText(byte[] bytes) {  
        int len = bytes.length;  
        StringBuilder buf = new StringBuilder(len * 2);  
        // 把密文转换成十六进制的字符串形式  
        for (int j = 0; j < len; j++) {  
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);  
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);  
        }  
        return buf.toString();  
    }
	
	/**
	 * SHA-256加密
	 * @param content
	 * @return
	 */
	public static String encrypt(String content){
		if(StringTools.isNullOrNone(content)){
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(content.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new LogicException("E001", "不支持SHA-256加密方式");
		}
	}
	
	/**
     * SHA-256校验
     * @param content 待校验内容
     * @param sha256 sha256值
     * @return
     */
    public static Boolean check(String content, String sha256){
        if (StringTools.isAnyNullOrNone(new String[]{content, sha256})) {
            return false;
        }
        return sha256.equals(SHA256Tools.encrypt(content));
    }
}
