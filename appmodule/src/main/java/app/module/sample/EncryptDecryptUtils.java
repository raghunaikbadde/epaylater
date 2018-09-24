package app.module.sample;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class EncryptDecryptUtils {

    public static String createChecksum(String input) throws IOException,
            NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes("UTF-8"));
        byte[] hash = md.digest();
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString().toUpperCase();
    }
    public static String encryptAES256AndBase64(String encryptionKey,
                                                String iv, String jsonBody) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new
                SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new
                IvParameterSpec(iv.getBytes("UTF-8")));
        byte[] encbyte = cipher.doFinal(jsonBody.getBytes("UTF-8"));
        return Base64.encodeToString(encbyte,Base64.NO_WRAP);
    }

    public static String encrypt(String key, String iv,String checkSum){
        String encodedString = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] keyBytes = new byte[16];
            byte[] b = key.getBytes("UTF-8");
            int len = b.length;
            if (len > keyBytes.length)
                len = keyBytes.length;
            System.arraycopy(b, 0, keyBytes, 0, len);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            byte[] results = cipher.doFinal(checkSum.getBytes("UTF-8"));
            encodedString = Base64.encodeToString(results,Base64.NO_WRAP);
        }catch (Exception e){
            return encodedString;
        }
        return encodedString;
    }
    public static String decryptBase64EncodedAES256(String encryptionKey,
                                                    String iv, String inputParam) throws Exception {
        byte[] decodedInput = Base64.decode(inputParam,Base64.NO_WRAP);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new
                SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new
                IvParameterSpec(iv.getBytes("UTF-8")));
        return new String(cipher.doFinal(decodedInput), "UTF-8");
    }

}
