
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;


public class AES {
    // set mode
    private static final String algorithm = "AES";
    private static final String padding = "AES/CBC/PKCS5Padding";
    private static final int KEY_LENGTH = 256;
    private static final int ITERATION_COUNT = 65536;


    public static SecretKeySpec getKeyFromPassword(String password, String salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        byte[] secretKey = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(secretKey, "AES");
    }

    public static byte[] mapToBytes(Map<String, User> map) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
        objStream.writeObject(map);
        objStream.close();
        return byteStream.toByteArray();
    }

    public static Map<String, User> bytesToMap(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objStream = new ObjectInputStream(byteStream);
        Map<String, User> map = (Map<String, User>) objStream.readObject();
        objStream.close();
        return map;
    }

    public static void encryptMapToFile(Map<String, User> map, SecretKeySpec key, File outputFile) throws Exception {
        byte[] mapBytes = mapToBytes(map);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[cipher.getBlockSize()];
        secureRandom.nextBytes(iv);
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
        byte[] encryptedBytes = cipher.doFinal(mapBytes);

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(iv); // Write IV to the beginning of the file
        outputStream.write(encryptedBytes);

        outputStream.close();
    }

    public static Map<String, User> decryptMapFromFile(SecretKeySpec key, File inputFile) throws Exception {
        FileInputStream inputStream = new FileInputStream(inputFile);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[cipher.getBlockSize()];
        inputStream.read(iv); // Read IV from the beginning of the file
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        byte[] encryptedBytes = new byte[(int) (inputFile.length() - iv.length)];
        inputStream.read(encryptedBytes);

        cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        inputStream.close();

        return bytesToMap(decryptedBytes);
    }






    public static byte[] mapToBytes1(Map<String, Password> map) throws IOException {
        ByteArrayOutputStream byteStream1 = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(byteStream1);
        objStream.writeObject(map);
        objStream.close();
        return byteStream1.toByteArray();
    }

    public static Map<String, Password> bytesToMap1(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream1 = new ByteArrayInputStream(bytes);
        ObjectInputStream objStream = new ObjectInputStream(byteStream1);
        Map<String, Password> map = (Map<String, Password>) objStream.readObject();
        objStream.close();
        return map;
    }
    public static void encryptPasswordsList(Map<String, Password> map, SecretKeySpec key, File outputFile) throws Exception {
        byte[] mapBytes = mapToBytes1(map);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[cipher.getBlockSize()];
        secureRandom.nextBytes(iv);
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivParams);
        byte[] encryptedBytes = cipher.doFinal(mapBytes);

        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputStream.write(iv); // Write IV to the beginning of the file
        outputStream.write(encryptedBytes);

        outputStream.close();
    }
    public static Map<String, Password> decryptPasswordsList(SecretKeySpec key, File inputFile) throws Exception {
        FileInputStream inputStream1 = new FileInputStream(inputFile);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[cipher.getBlockSize()];
        inputStream1.read(iv); // Read IV from the beginning of the file
        IvParameterSpec ivParams = new IvParameterSpec(iv);

        byte[] encryptedBytes = new byte[(int) (inputFile.length() - iv.length)];
        inputStream1.read(encryptedBytes);

        cipher.init(Cipher.DECRYPT_MODE, key, ivParams);
        byte[] decryptedBytes1 = cipher.doFinal(encryptedBytes);

        inputStream1.close();

        return bytesToMap1(decryptedBytes1);
    }
}
