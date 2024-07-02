import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.util.Base64;

public class DES3Encryptor {
    public static String encrypt(String input) {
        try {
            // Generate a 3DES key.
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede"); // Triple DES
            keyGenerator.init(168); // Key size is specified here.
            SecretKey secretKey = keyGenerator.generateKey();

            // Initialize Cipher for 3DES encryption.
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
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