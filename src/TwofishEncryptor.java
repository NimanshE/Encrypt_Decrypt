import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

public class TwofishEncryptor {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String encrypt(String input, String key) {
        try {
            // Ensure the key is exactly 256 bits (32 bytes).
            byte[] keyBytes = Arrays.copyOf(key.getBytes(), 32); // Pad or truncate to make sure it's 256 bits

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "Twofish");

            // Initialize the cipher for encryption.
            Cipher cipher = Cipher.getInstance("Twofish/ECB/PKCS5Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            // Encrypt the input string.
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());

            // Encode the encrypted bytes to Base64.
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}