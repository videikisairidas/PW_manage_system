import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;


public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String username;
    private final String password;
    private byte[] salt;

    public User(String username, String password) throws Exception {
        this.username = username;
        this.salt = getSalt();
        this.password = hashPassword(password, this.salt);
    }


    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Method to check if the password matches
    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User: " + username + " password:" + password;
    }

    // Method to hash the password using PBKDF2
    private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 65536;
        int keyLength = 256;
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return toHex(salt) + ":" + toHex(hash);
    }

    // Method to generate a salt
    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    // Method to convert byte array to hex string
    private String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    // Method to convert hex string to byte array
    private byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    // Method to check if the provided password matches the hashed password
    public boolean checkPassword(String password0) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeySpecException {
        String[] parts = password.split(":");
        byte[] salt = fromHex(parts[0]);
        byte[] hash = fromHex(parts[1]);

        int iterations = 65536;
        int keyLength = 256;
        PBEKeySpec spec = new PBEKeySpec(password0.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        return slowEquals(hash, testHash);
    }

    // Method to compare two byte arrays in length-constant time to prevent timing attacks
    private boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}
