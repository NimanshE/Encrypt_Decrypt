import org.bouncycastle.crypto.digests.WhirlpoolDigest;
import org.bouncycastle.util.encoders.Hex;

public class WhirlpoolEncryptor {
    public static String encrypt(String inputText) {
        WhirlpoolDigest digest = new WhirlpoolDigest();
        byte[] inputBytes = inputText.getBytes();
        digest.update(inputBytes, 0, inputBytes.length);
        byte[] hashOutput = new byte[digest.getDigestSize()];
        digest.doFinal(hashOutput, 0);
        return new String(Hex.encode(hashOutput));
    }
}