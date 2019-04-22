package com.cc.common.tools;

import com.cc.common.exception.LogicException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密
 * Created by yuanwenshu on 2018/9/4.
 */
public class RSATools {

    public static String PRIVATEKEY = "privateKey";

    public static String PUBLICKEY = "publicKey";

    /**
     * 获取随机非对称密码对
     * @param length
     * @return 秘钥对使用base64编码
     */
    public static Map<String, String> initKey(int length){
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new LogicException("E001", "不支持RSA加密方式");
        }
        keyPairGenerator.initialize(length);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Map<String, String> keyMap = new HashMap<String, String>(2);
        keyMap.put(RSATools.PRIVATEKEY, Base64.encodeBase64String(keyPair.getPrivate().getEncoded()));
        keyMap.put(RSATools.PUBLICKEY, Base64.encodeBase64String(keyPair.getPublic().getEncoded()));
        return keyMap;
    }

    /**
     * 获取公钥
     * @param publicKey 使用base64编码
     * @return
     */
    private static RSAPublicKey getPublicKey(String publicKey){
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new LogicException("E001", "不支持RSA加密方式");
        }
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey rsaPublicKey = null;
        try {
            rsaPublicKey = (RSAPublicKey)keyFactory.generatePublic(x509KeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new LogicException("E002", "无效公钥");
        }
        return rsaPublicKey;
    }

    /**
     * 获取私钥
     * @param privateKey 使用base64编码
     * @return
     */
    private static RSAPrivateKey getPrivateKey(String privateKey){
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new LogicException("E001", "不支持RSA加密方式");
        }
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey rsaPrivateKey = null;
        try {
            rsaPrivateKey = (RSAPrivateKey)keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new LogicException("E003", "无效私钥");
        }
        return rsaPrivateKey;
    }

    /**
     * 公钥加密
     * @param content 待加密内容
     * @param publicKey 使用base64编码
     * @return 使用base64编码
     */
    public static String publicEncrypt(String content, String publicKey){
        if (StringTools.isNullOrNone(content)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            byte[] bytes = cipher.doFinal(content.getBytes());
            return Base64.encodeBase64String(bytes);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E004", "加密错误");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new LogicException("E004", "加密错误");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E004", "加密错误");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new LogicException("E002", "无效公钥");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new LogicException("E001", "不支持RSA加密方式");
        }
    }

    /**
     * 公钥解密
     * @param content 待解密内容 使用base64编码
     * @param publicKey 使用base64编码
     * @return
     */
    public static String publicDecrypt(String content, String publicKey){
        if (StringTools.isNullOrNone(content)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, getPublicKey(publicKey));
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result, Charset.forName("UTF-8"));
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E005", "解密错误");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new LogicException("E005", "解密错误");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E005", "解密错误");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new LogicException("E002", "无效公钥");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new LogicException("E001", "不支持RSA加密方式");
        }
    }

    /**
     * 私钥加密
     * @param content 待加密内容
     * @param privateKey 使用base64编码
     * @return 使用base64编码
     */
    public static String privateEncrypt(String content, String privateKey){
        if (StringTools.isNullOrNone(content)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, getPrivateKey(privateKey));
            byte[] bytes = cipher.doFinal(content.getBytes(Charset.forName("UTF-8")));
            return Base64.encodeBase64String(bytes);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E004", "加密错误");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new LogicException("E004", "加密错误");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E004", "加密错误");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new LogicException("E003", "无效私钥");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new LogicException("E001", "不支持RSA加密方式");
        }
    }

    /**
     * 私钥解密
     * @param content 待解密内容 使用base64编码
     * @param privateKey 使用base64编码
     * @return
     */
    public static String privateDecrypt(String content, String privateKey){
        if (StringTools.isNullOrNone(content)) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            byte[] bytes = cipher.doFinal(Base64.decodeBase64(content));
            return new String(bytes, Charset.forName("UTF-8"));
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E005", "解密错误");
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new LogicException("E005", "解密错误");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new LogicException("E005", "解密错误");
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new LogicException("E003", "无效私钥");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new LogicException("E001", "不支持RSA加密方式");
        }
    }
}
