package Encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class RC4Encryptor {
    public static String encrypt(String inputText) {
        try {
            // Load the RC4 key from the file
            String base64Key = rc4KeyGenUtil.loadRC4Key();
            // Decode the Base64-encoded key to get the binary key bytes
            byte[] keyBytes = Base64.getDecoder().decode(base64Key);

            // Create a key specification for RC4
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "RC4");

            // Initialize the Cipher for RC4 encryption
            Cipher cipher = Cipher.getInstance("RC4");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            // Perform the encryption
            byte[] encryptedBytes = cipher.doFinal(inputText.getBytes());

            // Encode the encrypted bytes as a Base64 string to ensure safe printing or storage
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "Encryption error: " + e.getMessage();
        }
    }
}