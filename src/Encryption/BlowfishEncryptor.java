package Encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class BlowfishEncryptor {
    public static String encrypt(String input) {
        try {
            // Generate a Blowfish key.
            KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
            SecretKey secretKey = keyGenerator.generateKey();

            // Initialize Cipher for Blowfish encryption.
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Encrypt the data.
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());

            // Encode the encrypted bytes to Base64 to get a string.
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}