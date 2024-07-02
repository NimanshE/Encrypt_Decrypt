import org.bouncycastle.jce.provider.BouncyCastleProvider;
import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class ECCEncryptor {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String PUBLIC_KEY_PATH = "src/keys/ecc_public.key";

    public static void initializeKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(256); // Using a 256-bit curve
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Save the public key to a file
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        Files.write(Paths.get(PUBLIC_KEY_PATH), publicKeyBytes);
    }

    public static PublicKey loadPublicKey() throws Exception {
        byte[] publicKeyBytes = Files.readAllBytes(Paths.get(PUBLIC_KEY_PATH));
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePublic(keySpec);
    }

    public static String encrypt(String plaintext) {
        try {
            PublicKey publicKey = loadPublicKey();
            Cipher cipher = Cipher.getInstance("ECIES", "BC"); // Use Bouncy Castle provider
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "Encryption error: " + e.getMessage();
        }
    }
}