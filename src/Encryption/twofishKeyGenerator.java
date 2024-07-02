package Encryption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class twofishKeyGenerator {
    public static void generateTwofishKey(String filePath) {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32]; // 256 bits
        random.nextBytes(key);
        try {
            Files.write(Paths.get(filePath), key);
            System.out.println("Key generated and saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to write key to file: " + e.getMessage());
        }
    }
}