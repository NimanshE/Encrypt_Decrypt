import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

public class ElGamalEncryptor {
    public static String encrypt(String message, ElGamalKeyManager keyManager) {
        SecureRandom rand = new SecureRandom();
        BigInteger m = new BigInteger(message.getBytes());
        BigInteger k = new BigInteger(Integer.parseInt("512"), rand).mod(keyManager.getP().subtract(BigInteger.valueOf(2))).add(BigInteger.valueOf(2)); // Random key
        BigInteger c1 = keyManager.getG().modPow(k, keyManager.getP());
        BigInteger c2 = keyManager.getY().modPow(k, keyManager.getP()).multiply(m).mod(keyManager.getP());

        // Convert c1 and c2 to a string format
        String c1Str = Base64.getEncoder().encodeToString(c1.toByteArray());
        String c2Str = Base64.getEncoder().encodeToString(c2.toByteArray());
        return c1Str + ":" + c2Str;
    }
}