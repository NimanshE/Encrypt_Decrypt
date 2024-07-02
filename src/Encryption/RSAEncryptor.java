package Encryption;

import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAEncryptor {
    private static final String PUBLIC_KEY_FILE = "src/keys/rsa_public.key";

    // Generates an RSA key pair and saves the public key to a file in X.509 format
    public static void generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        PublicKey publicKey = pair.getPublic();

        // Saving the public key to a file in X.509 format
        try (FileOutputStream fos = new FileOutputStream(PUBLIC_KEY_FILE)) {
            byte[] publicKeyBytes = publicKey.getEncoded();
            fos.write(publicKeyBytes);
        }
    }

    // Loads the RSA public key from a file
    public static PublicKey loadPublicKey() throws Exception {
        byte[] publicKeyBytes = Files.readAllBytes(Paths.get(PUBLIC_KEY_FILE));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    // Encrypts a string using the RSA public key
    public static String encrypt(String plainText) throws Exception {
        PublicKey publicKey = loadPublicKey();
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}