package Encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA3Encryptor {
    public static String encrypt(String inputText) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA3-256");
        byte[] messageDigest = md.digest(inputText.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}