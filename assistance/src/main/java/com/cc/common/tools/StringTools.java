/**
 * 
 */
package com.cc.common.tools;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * @author Administrator
 *
 */
public class StringTools {

	/**
	 * 字符串是否为空
	 * @param s
	 * @return
	 */
	public static boolean isNullOrNone(String s) {
        return (s == null || "".equals(s.trim()));
    }

	/**
	 * 字符串数组任意元素为空
	 * @param objects
	 * @return
	 */
	public static boolean isAnyNullOrNone(String[] objects) {
		if(objects == null || objects.length == 0){
			return true;
		}
		for (String object : objects) {
			if(StringTools.isNullOrNone(object)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 字符串数组所有元素为空
	 * @param objects
	 * @return
	 */
	public static boolean isAllNullOrNone(String[] objects) {
		if(objects == null || objects.length == 0){
			return true;
		}
		for (String object : objects) {
			if(!StringTools.isNullOrNone(object)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * object to 字符串
	 * @param object
	 * @return
	 */
	public static String toString(Object object){
		if (object==null) {
			return null;
		}
		return String.valueOf(object);
	}

	/**
	 * 字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str) {
		return (str != null && !"".equals(str)) && Pattern.matches("^[0-9.]+$", str);
	}

	/**
	 * 获取序列号
	 * @return
	 */
	public static synchronized String getSeqNo() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(DateTools.getFormatDate(new Date(), DateTools.DATEFORMAT2));
		int _int = 0;
		while(_int<4){
			Random random = new Random();
			buffer.append(random.nextInt(10));
			_int++;
		}
		return buffer.substring(0);
	}
	
	public static synchronized String getSys36SeqNo() {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer buffer = new StringBuffer();
		String date = DateTools.getFormatDate(new Date(), DateTools.DATEFORMAT2);
		buffer.append(chars.charAt(Integer.parseInt(date.substring(2, 3))%chars.length()));
		buffer.append(chars.charAt(Integer.parseInt(date.substring(4, 5))%12));
		buffer.append(chars.charAt(Integer.parseInt(date.substring(6, 7))%31));
		buffer.append(chars.charAt(Integer.parseInt(date.substring(8, 9))%24));
		buffer.append(chars.charAt(Integer.parseInt(date.substring(10, 11))%chars.length()));
		buffer.append(chars.charAt(Integer.parseInt(date.substring(12, 13))%chars.length()));
		int _int = 0;
		while(_int<4){
			Random random = new Random();
			buffer.append(random.nextInt(10));
			_int++;
		}
		return buffer.substring(0);
	}
	
	/**
	 * 获取指定长度的随机字符串
	 * @param length
	 * @return
	 */
	public static String getRandomCode(int length) {
		String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer buffer = new StringBuffer();
		int _int = 0;
		while(_int<length){
			Random random = new Random();
			buffer.append(chars.charAt(random.nextInt(chars.length())));
			_int++;
		}
		return buffer.substring(0);
	}

	/**
	 * 人民币分转换成元
	 * @param cent
	 * @return
	 */
	public static String toRmbYuan(String cent){
		if(StringTools.isNullOrNone(cent)){
			return null;
		}
		double _cent = Double.valueOf(cent);
		DecimalFormat df = new DecimalFormat("0.##");
		try {
			String yuan = df.format(_cent/100);
			return yuan;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	/**
	 * 人民币元转换成分
	 * @return
	 */
	public static String toRmbCent(String yuan){
		if(StringTools.isNullOrNone(yuan)){
			return null;
		}
		double _yuan = Double.valueOf(yuan);
		DecimalFormat df = new DecimalFormat("0");
		try {
			String cent = df.format(_yuan*100);
			return cent;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
	
	/**
	 * 是否符合正则表达式
	 * @param content
	 * @param regEx
	 * @return
	 */
	public static boolean matches(String content, String regEx){
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(content);
		return matcher.matches();
	}
	
	/**
	 * 字符串长度是否小于length
	 * @param content
	 * @param length
	 * @return
	 */
	public static boolean lessThan(String content, int length){
		return StringTools.isNullOrNone(content) || content.length()<length;
	}
}
