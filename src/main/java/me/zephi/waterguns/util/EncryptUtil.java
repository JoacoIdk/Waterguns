package me.zephi.waterguns.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
    public static String encrypt(String message, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = message.getBytes();
            byte[] output = md.digest(bytes);

            return new String(output);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String encryptSHA3_256(String message) {
        return encrypt(message, "SHA3-256");
    }
}
