package com.atti;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * By default, it will check the user's home directory for keys unless a path to
 * the key file is specified. Key/IVs can be loaded once and reused if needed.
 * Defaults to AES 128, CBC, PKCS5 Key/IV generated using SHA1PRNG
 * 
 * @author nmatatall
 */
public class DefaultPasswordService {
  protected static final String KEY_PATH_PROPERTY = "atti.key.path";
  protected static final String PASSWORD_FILE_DIRECTORY = "atti.pw.dir";

  protected static final String TRANSFORMATION = "AES/CBC/PKCS5PADDING";
  protected static final String ENCRYPTION_ALG = "AES";
  protected static final String SECURE_RANDOM_ALG = "SHA1PRNG";
  protected static final int KEY_SIZE = 128;

  private static final Logger log = Logger
      .getLogger(DefaultPasswordService.class);

  /**
   * Kept just in case the IV is only a subset of keyBytes (e.g. if the byte[]
   * is a composite of data/key/iv).
   */
  private static IvParameterSpec bytesToInitVector(byte[] ivBytes) {
    return new IvParameterSpec(ivBytes);
  }

  /**
   * Kept just in case the key is only a subset of keyBytes (e.g. if the byte[]
   * is a composite of data/key/iv).
   */
  private static SecretKeySpec bytesToKey(byte[] keyBytes) {
    return new SecretKeySpec(keyBytes, ENCRYPTION_ALG);
  }

  /**
   * Reads the file specified by pwFilePath. If pwFilePath contains
   * {@link File#separator}, then it is treated like a path. Otherwise, it is
   * treated as a file name and this method will attempt to look up the file in
   * the user's home directory or the value for the system property
   * {@link DefaultPasswordService#KEY_PATH_PROPERTY}
   */
  public static String getPassword(String pwFilePath) {
    if (!pwFilePath.contains(File.separator) && !new File(pwFilePath).exists()) {
      String path = System.getProperty(PASSWORD_FILE_DIRECTORY);
      if (path == null) {
        pwFilePath = KeyLoader.DEFAULT_LOCATION + pwFilePath;
      } else {
        pwFilePath = path + '/' + pwFilePath;
      }
      log.info("Path appears to be a filename, full path=" + pwFilePath);
    }

    log.info("Attempting to decrypt file at " + pwFilePath);

    byte[] decodedCipherText = null;
    try {
      BufferedReader in = new BufferedReader(new FileReader(pwFilePath));
      decodedCipherText = Base64.decodeBase64(in.readLine().getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Couldn't read password file", e);
    }
    
    return decrypt(new String(decodedCipherText));
  }

  /**
   * Decrypts ciphertext using the key/iv stored in KEY_PATH_PROPERTY if
   * available, otherwise the default location stored in KeyLoader
   * 
   * @param cipherText
   *          the value to be decrypted
   * @return decrypted cipherText
   */
  public static String decrypt(String cipherText) {
    return decrypt(cipherText, System.getProperty(KEY_PATH_PROPERTY));
  }

  /**
   * Decrypts cipherText using the key/iv specified
   * 
   * @return decrypted cipherText
   */
  public static String decrypt(String cipherText, SecretKey key,
      IvParameterSpec iv) {
    Cipher cipher;
    try {
      cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(Cipher.DECRYPT_MODE, key, iv);
      return new String(cipher.doFinal(cipherText.getBytes()));
    } catch (GeneralSecurityException e) {
      // All Exceptions here cannot be handled by caller
      log.error(e);
      throw new RuntimeException("Could not decrypt text.  Were the right keys used?", e);
    }
  }

  /**
   * Decrypts ciphertext using the key/iv stored in KEY_PATH_PROPERTY if
   * available, otherwise the default location stored in KeyLoader
   * 
   * @param cipherText
   *          the value to be decrypted
   * @param path
   *          the location of the key/iv file
   * @return unencrypted version of cipherText
   */
  private static String decrypt(String cipherText, String path) {
    SecretKeyWithIV keyInfo = KeyLoader.load(path);
    SecretKeySpec key = bytesToKey(keyInfo.getKey());
    IvParameterSpec iv = bytesToInitVector(keyInfo.getIv());
    return decrypt(cipherText, key, iv);
  }

  /**
   * Encrypts plaintext using the key/iv stored in KEY_PATH_PROPERTY if
   * available, otherwise the default location stored in KeyLoader
   * 
   * @param plaintext
   *          the value to be encrypted
   * @return AES encrypted value
   */
  public static String encrypt(String plaintext) {
    String keypath = System.getProperty(KEY_PATH_PROPERTY);
    return encrypt(plaintext, keypath);
  }

  /**
   * Encrypts plaintext using the key/iv specified
   * 
   * @return encrypted cipherText
   */
  public static String encrypt(String plaintext, SecretKey key,
      IvParameterSpec iv) {
    Cipher cipher;
    try {
      cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(Cipher.ENCRYPT_MODE, key, iv);
      return new String(cipher.doFinal(plaintext.getBytes()));
    } catch (GeneralSecurityException e) {
      // All Exceptions here cannot be handled by caller
      throw new RuntimeException("Could not encrypt password.", e);
    }
  }

  /**
   * Encrypts plaintext using the key/iv stored in the specified path available,
   * otherwise the default location stored in KeyLoader
   * 
   * @param plaintext
   *          the value to be encrypted
   * @param keyPath
   *          the path to the key/iv file
   * @return AES encrypted value
   */
  public static String encrypt(String plaintext, String keyPath) {
    SecretKeyWithIV keyInfo = KeyLoader.load(keyPath);

    SecretKeySpec key = bytesToKey(keyInfo.getKey());
    IvParameterSpec iv = bytesToInitVector(keyInfo.getIv());
    return encrypt(plaintext, key, iv);
  }

  /**
   * @return a 16 byte IV
   */
  public static IvParameterSpec generateIV() {
    log.info("Generating a new IV....");
    byte[] ivBytes = new byte[16];
    SecureRandom secureRandom = null;
    try {
      secureRandom = SecureRandom.getInstance(SECURE_RANDOM_ALG);
    } catch (NoSuchAlgorithmException e) {
      log.error("Error generating IV", e);
      throw new RuntimeException(
          "Should never happen - contact the security team", e);
    }

    secureRandom.nextBytes(ivBytes);
    return new IvParameterSpec(ivBytes);
  }

  /**
   * @return an AES key that is KEY_SIZE bits long
   */
  public static SecretKey generateKey() {
    log.info("Generating a new key....");
    KeyGenerator kg = null;
    try {
      kg = KeyGenerator.getInstance(ENCRYPTION_ALG);
    } catch (NoSuchAlgorithmException e) {
      log.error("Error generating key", e);
      throw new RuntimeException(
          "Should never happen - contact the security team", e);
    }
    kg.init(KEY_SIZE);
    return kg.generateKey();
  }
}
