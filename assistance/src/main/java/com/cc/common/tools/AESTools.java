package com.cc.common.tools;

import com.cc.common.exception.LogicException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * AES加密
 * Created by yuanwenshu on 2018/8/28.
 */
public class AESTools {

    /**
     * aes加密
     * @param content 待加密内容
     * @param key 长度必须是8的倍数
     * @return
     */
    public static String encrypt(String content, String key){
        if (StringTools.isNullOrNone(content)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(Charset.forName("UTF-8")));
            cipher.init(Cipher.ENCRYPT_MODE, getSecurekey(key), iv);
            byte[] result = cipher.doFinal(content.getBytes(Charset.forName("UTF-8")));
            return Base64.encodeBase64String(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new LogicException("E001", "不支持AES加密方式");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new LogicException("E002", "无效秘钥");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new LogicException("E002", "无效秘钥");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E003", "加密错误");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E003", "加密错误");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new LogicException("E003", "加密错误");
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            throw new LogicException("E003", "加密错误");
        }
    }

    /**
     * aes解密
     * @param content 待解密内容
     * @param key 长度必须是8的倍数
     * @return
     */
    public static String decrypt(String content, String key){
        if (StringTools.isNullOrNone(content)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(key.getBytes(Charset.forName("UTF-8")));
            cipher.init(Cipher.DECRYPT_MODE, getSecurekey(key), iv);
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, Charset.forName("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new LogicException("E001", "不支持AES加密方式");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new LogicException("E002", "无效秘钥");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E004", "解密错误");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E004", "解密错误");
        }  catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new LogicException("E004", "解密错误");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new LogicException("E002", "无效秘钥");
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            throw new LogicException("E004", "解密错误");
        }
    }
    
    /**
     * 微信加密数据aes解密
     * @param content 待解密内容
     * @param key 长度必须是8的倍数
     * @param iv 初始向量
     * @return
     */
    public static String decrypt(String content, String key, String iv){
        if (StringTools.isNullOrNone(content)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.decodeBase64(iv));
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key), "AES"), ivParameterSpec);
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, Charset.forName("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new LogicException("E001", "不支持AES加密方式");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new LogicException("E002", "无效秘钥");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E004", "解密错误");
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E004", "解密错误");
        }  catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new LogicException("E004", "解密错误");
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            throw new LogicException("E004", "解密错误");
        }
    }

    private static SecretKey getSecurekey(String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(Charset.forName("UTF-8")), "AES");
        return keyspec;
    }
}
