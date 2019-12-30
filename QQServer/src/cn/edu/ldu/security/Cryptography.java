package cn.edu.ldu.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

public class Cryptography {
    private static final int BUFSIZE=8192; 
  
    public static String getHash(String plainText,String hashType) {
        try {
            MessageDigest md=MessageDigest.getInstance(hashType);
            byte[] encryptStr=md.digest(plainText.getBytes("UTF-8"));
            return DatatypeConverter.printHexBinary(encryptStr);        
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            return null;
      }
    }
    public static SecretKey generateNewKey() {
        try {
          
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            SecretKey secretKey=keyGenerator.generateKey();
            return secretKey;
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }
}
