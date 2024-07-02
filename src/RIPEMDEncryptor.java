import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

public class RIPEMDEncryptor {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String encrypt(String inputText) throws NoSuchAlgorithmException {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("RIPEMD160", "BC");
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        }
        byte[] messageDigest = digest.digest(inputText.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : messageDigest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}