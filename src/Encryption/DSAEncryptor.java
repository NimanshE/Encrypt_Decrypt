package Encryption;

import java.security.*;
import java.util.Base64;

public class DSAEncryptor {
    // Generates a DSA key pair
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        keyGen.initialize(2048, random);
        return keyGen.generateKeyPair();
    }

    // Signs a message using a private key
    public static String sign(String message, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature dsa = Signature.getInstance("SHA1withDSA");
        dsa.initSign(privateKey);
        dsa.update(message.getBytes());
        byte[] signature = dsa.sign();
        return Base64.getEncoder().encodeToString(signature);
    }

    // Verifies a signature using a public key
    public static boolean verify(String message, String signature, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sig = Signature.getInstance("SHA1withDSA");
        sig.initVerify(publicKey);
        sig.update(message.getBytes());
        return sig.verify(Base64.getDecoder().decode(signature));
    }
}