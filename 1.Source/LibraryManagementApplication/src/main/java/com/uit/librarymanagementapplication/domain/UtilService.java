package com.uit.librarymanagementapplication.domain;

import com.uit.librarymanagementapplication.AppConfig;
import com.uit.librarymanagementapplication.lib.Constants;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class UtilService {

    private static final String SECRET_KEY = AppConfig.getInstance().hashKey;

    public static String encrypt(String plainText) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), Constants.ALGORITHM);
            Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {

        }
        return "";
    }

    public static String decrypt(String encryptedText) {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), Constants.ALGORITHM);

        try {
            Cipher cipher = Cipher.getInstance(Constants.ALGORITHM);

            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {

        }
        return "";
    }

}
