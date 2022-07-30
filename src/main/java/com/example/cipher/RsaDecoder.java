package com.example.cipher;

import com.example.constant.RsaConstant;
import com.example.exception.DecryptBeyondLengthException;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author 陈磊
 * @version 2.0
 * @date 2022/7/14 23:39
 */
@Component
public class RsaDecoder {

    public static final int MAX_ENCRYPT_LENGTH = 117;

    public static final int MAX_DECRYPT_LENGTH = 128;

    // 私钥转换
    private static final PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(RsaConstant.prikey));

    // 公钥转换
    private static final X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(RsaConstant.pubkey));

    /**
     * @param mi 密文
     * @return 返回明文
     * 基于rsa解密 解密最大长度是128字节 解密字符串
     * */
    public String deCode(String mi) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException {
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher1 = Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] minwen = cipher1.doFinal(Base64.getDecoder().decode(mi.getBytes(Charset.defaultCharset())));
        return new String(minwen);
    }

    /**
     * @param src 源加密语句
     * @return 返回密文
     * 基于rsa加密 加密最大长度是117字节 加密字符串
     * */
    public String enCode(String src) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] cypher = cipher.doFinal("guest".getBytes(Charset.defaultCharset()));
        return new String(Base64.getEncoder().encode(cypher), Charset.defaultCharset());
    }

    /**
     * @param mi 原字节流
     * @return 返回解密字节流
     * 基于rsa 解密最大长度是128字节 解密字节流
     * */
    public byte[] deCodeFiles(byte[] mi) throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, InvalidKeySpecException, DecryptBeyondLengthException {
        if (mi.length > RsaDecoder.MAX_DECRYPT_LENGTH) {
            throw new DecryptBeyondLengthException("超过最大解长度128");
        }

        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher1 = Cipher.getInstance("RSA");
        cipher1.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher1.doFinal(mi);
    }

    /**
     * @param src 源加密字节流
     * @return 返回密文
     * 基于rsa加密 加密最大长度是117字节 加密字符串
     * */
    public byte[] enCodeFiles(byte[] src) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, DecryptBeyondLengthException {
        if (src.length > RsaDecoder.MAX_ENCRYPT_LENGTH) {
            throw new DecryptBeyondLengthException("超过最大加密长度117");
        }

        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        return cipher.doFinal(src);
    }

    public boolean checkSign(String origin) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, InvalidKeySpecException {
        Signature signature = Signature.getInstance("SHA1WithRSA");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(RsaConstant.prikey));
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
        signature.initSign(privateKey);
        signature.update("chenlei".getBytes(Charset.defaultCharset()));
        byte[] passFlag = signature.sign();
        System.out.println(new String(Base64.getEncoder().encode(passFlag), Charset.defaultCharset()));

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(RsaConstant.pubkey));
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);

        Signature signature1 = Signature.getInstance("SHA1WithRSA");
        signature1.initVerify(publicKey);
        signature1.update(origin.getBytes(Charset.defaultCharset()));
        return signature1.verify(passFlag);
    }
}
