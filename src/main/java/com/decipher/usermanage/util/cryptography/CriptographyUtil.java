package com.decipher.usermanage.util.cryptography;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;

import com.decipher.usermanage.util.UserLogger;
import com.sun.mail.util.*;
import org.apache.log4j.BasicConfigurator;
import org.springframework.stereotype.Component;

/**
 * Created by decipher19 on 23/3/17.
 */
@Component
public class CriptographyUtil {

    private static Cipher ecipher;
    private static Cipher dcipher;
    private static SecretKey key;
    private static final String DATA_ENCRIPTOR_STANDARD_TEXT ="DES";
    private CriptographyUtil() {

    }

    static {

            try {
                key = KeyGenerator.getInstance(DATA_ENCRIPTOR_STANDARD_TEXT).generateKey();
                ecipher = Cipher.getInstance(DATA_ENCRIPTOR_STANDARD_TEXT);
                dcipher = Cipher.getInstance(DATA_ENCRIPTOR_STANDARD_TEXT);
                // initialize the ciphers with the given key

                ecipher.init(Cipher.ENCRYPT_MODE, key);

                dcipher.init(Cipher.DECRYPT_MODE, key);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
                UserLogger.error(e);
            }

    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
            // generate secret key using DES algorithm

//            String encrypted = encrypt("b.s.j adoun91@gmail.com");

//            String hexDecimal=toHexadecimal(encrypted);
//            String encryptedString=hexToString(hexDecimal);
//            String decrypted = decrypt(encrypted);
//            UserLogger.info("toHexadecimal :"+hexDecimal);
//            UserLogger.info("hexToString : "+encryptedString);
//            UserLogger.info("Decrypted: " + decrypted);

    }

    /***
     * convert String literal into hexaDecimal String
     * @param text
     * @return
     */
    public static String toHexadecimal(String text)
    {
        byte[] myBytes = new byte[0];
        try {
            myBytes = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            UserLogger.error(e);
        }
        return DatatypeConverter.printHexBinary(myBytes);
    }

    /****
     * covert hexaDecimal literal into String text
     * @param hex
     * @return
     */
    public static String hexToString(String hex) {
        return new String(new BigInteger(hex, 16).toByteArray());
    }

        /****
         * Encript plainText based on 64(BASE64EncoderStream)
         * @param plainText
         * @return
         */
    public static String encrypt(String plainText) {

        try {
            // encode the string into a sequence of bytes using the named charset

            // storing the result into a new byte array.

            byte[] utf8 = plainText.trim().getBytes("UTF8");

            byte[] encode = ecipher.doFinal(utf8);

            // encode to base64

            encode = BASE64EncoderStream.encode(encode);

            return toHexadecimal(new String(encode));
        } catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            UserLogger.error(e);
        }
    return null;
  }

    /***
     * decript encripted BASE64DecoderStream plaintext
     * @param encriptedText
     * @return
     */

    public static String decrypt(String encriptedText) {

        try {
            // decode with base64 to get bytes

            byte[] decode = BASE64DecoderStream.decode(hexToString(encriptedText).getBytes());

            byte[] utf8 = dcipher.doFinal(decode);

            // create new string based on the specified charset

            return new String(utf8, "UTF8");
        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
            UserLogger.error(e);
        }
        return null;
    }
}