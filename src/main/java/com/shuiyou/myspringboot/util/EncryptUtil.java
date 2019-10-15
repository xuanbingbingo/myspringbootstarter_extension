package com.shuiyou.myspringboot.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class EncryptUtil {

    /**
     * 生成密钥对
     * @return 密钥对
     * @throws NoSuchAlgorithmException
     */
    public static Map<Integer, String> genKeyPair() throws NoSuchAlgorithmException {
        Map<Integer, String> keyMap = new HashMap<Integer, String>();
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        // 得到私钥字符串
        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
        // 将公钥和私钥保存到Map
        keyMap.put(0,publicKeyString);  //0表示公钥
        keyMap.put(1,privateKeyString);  //1表示私钥
        return keyMap;
    }

    /**
     * 使用私钥进行解密
     * @param bt_encrypted  需要解密的字节数组
     * @param privateKeyStr 私钥
     * @return 解密的字节数组
     * @throws Exception 异常
     */
    public static byte[] decryptByPrivateKey(byte[] bt_encrypted, String privateKeyStr) throws Exception {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bt_original = cipher.doFinal(bt_encrypted);
        return bt_original;
    }

    /**
     * 使用私钥进行加密
     * @param bt_plaintext  需要加密的字节数组
     * @param privateKeyStr 私钥
     * @return 解密的字节数组
     * @throws Exception 异常
     */
    public static byte[] encryptByPrivateKey(byte[] bt_plaintext, String privateKeyStr) throws Exception {
        PrivateKey privateKey = getPrivateKey(privateKeyStr);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] bt_original = cipher.doFinal(bt_plaintext);
        return bt_original;
    }

    /**
     * 获取私钥
     * @param key 秘钥
     * @return 公钥
     * @throws Exception 异常
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decodeBase64(key);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    /**
     * 使用公钥进行解密
     * @param bt_encrypted  需要解密的字节数组
     * @param publicKeyStr 公钥
     * @return 解密的字节数组
     * @throws Exception 异常
     */
    public static byte[] decryptByPublicKey(byte[] bt_encrypted, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] bt_original = cipher.doFinal(bt_encrypted);
        return bt_original;
    }

    /**
     * 使用公钥进行加密
     * @param bt_plaintext 需要加密的字节数组
     * @param publicKeyStr 公钥
     * @return 加密的字节数组
     * @throws Exception 异常
     */
    public static byte[] encryptByPublicKey(byte[] bt_plaintext, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bt_encrypted = cipher.doFinal(bt_plaintext);
        return bt_encrypted;
    }

    /**
     * 获取公钥
     * @param key 秘钥
     * @return 公钥
     * @throws Exception 异常
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decodeBase64(key);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * AES加密
     * @param encodeRules 秘钥
     * @param content 待加密内容
     * @return 加密内容
     * @throws Exception 异常
     */
    public static String AESEncode(String encodeRules, String content) throws Exception {

        try {
            // 生成AES密钥
            SecretKey key = getAESKey(encodeRules);
            // 根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 获取加密内容的字节数组
            byte[] byte_encode = content.getBytes("utf-8");
            // 根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            // 将加密后的数据转换为字符串
            String AES_encode = new String(Base64.encodeBase64(byte_AES));
            return AES_encode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果有错就返加null
        return null;
    }

    /**
     * AES解密
     * @param encodeRules 秘钥
     * @param content 待解密内容
     * @return 解密内容
     * @throws Exception 异常
     */
    public static String AESDecode(String encodeRules, String content) throws Exception {
        try {
            // 生成AES密钥
            SecretKey key = getAESKey(encodeRules);
            // 根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES");
            // 初始化密码器
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 将加密并编码后的内容解码成字节数组
            byte[] byte_content = Base64.decodeBase64(content);
            // 解密
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode);
            return AES_decode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果有错就返加null
        return null;
    }

    /**
     * 生成AES密钥
     * @param encodeRules 秘钥
     * @return AES密钥
     * @throws Exception 异常
     */
    public static SecretKey getAESKey(String encodeRules) throws Exception {
        // 创建密钥生成器
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(encodeRules.getBytes());

        // 初始化密钥
        keyGenerator.init(128, random);
        // 生成密钥
        SecretKey getKey = keyGenerator.generateKey();
        byte[] raw = getKey.getEncoded();
        // 根据字节数组生成AES密钥
        SecretKey key = new SecretKeySpec(raw, "AES");
        return key;
    }


    public static String buildSign(byte[] data, String privateKey){
        try{
            // 解密由base64编码的私钥
            byte[] keyBytes = Base64.decodeBase64(privateKey);
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            // 私钥信息生成数字签名
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(priKey);
            signature.update(data);
            return Base64.encodeBase64String(signature.sign());
        } catch (Exception e){
            throw new RuntimeException("buildSign exception" + e);
        }



    }


}
