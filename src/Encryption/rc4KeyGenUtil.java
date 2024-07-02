package Encryption;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class rc4KeyGenUtil {
    private static final String KEY_PATH = "src/keys/rc4.key"; // Path to save the key

    public static void generateAndSaveRC4Key() throws NoSuchAlgorithmException, IOException {
        String base64Key = generateRC4KeyAsString();
        Files.write(Paths.get(KEY_PATH), base64Key.getBytes());
    }

    public static String loadRC4Key() throws IOException {
        return new String(Files.readAllBytes(Paths.get(KEY_PATH)));
    }

    private static String generateRC4KeyAsString() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("RC4");
        SecureRandom secureRandom = new SecureRandom();
        keyGen.init(128, secureRandom); // RC4 keys can range from 40 to 2048 bits, 128 is a common choice
        SecretKey key = keyGen.generateKey();
        // Encode the key as a Base64 string
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
}