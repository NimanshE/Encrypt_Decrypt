package Encryption;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ElGamalKeyManager {
    private static final BigInteger TWO = new BigInteger("2");
    private BigInteger p, g, y, x; // p = prime, g = generator, y = public key, x = private key

    public ElGamalKeyManager() {
        generateKeys();
    }

    private void generateKeys() {
        SecureRandom rand = new SecureRandom();
        p = BigInteger.probablePrime(512, rand); // Prime number
        g = TWO; // Simple generator
        x = new BigInteger(Integer.parseInt("512"), rand).mod(p.subtract(TWO)).add(TWO); // Private key
        y = g.modPow(x, p); // Public key
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getY() {
        return y;
    }

    public BigInteger getX() {
        return x;
    }
}