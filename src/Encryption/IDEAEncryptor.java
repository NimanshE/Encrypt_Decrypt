package Encryption;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Base64;

public class IDEAEncryptor {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    // Define a static key. In a real application, manage this securely.
    private static final String KEY = "your-secure-128bit-key"; // Ensure this is a 128-bit key

    public static String encrypt(String input) throws Exception {
        Cipher cipher = Cipher.getInstance("IDEA/ECB/PKCS5Padding", "BC");
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "IDEA");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    // Assuming you might also want a matching decrypt method
    public static String decrypt(String input) throws Exception {
        byte[] output = Base64.getDecoder().decode(input);
        Cipher cipher = Cipher.getInstance("IDEA/ECB/PKCS5Padding", "BC");
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "IDEA");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decrypted = cipher.doFinal(output);
        return new String(decrypted);
    }
}