package com.atti;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultPasswordServiceTest {

  /**
   * Used to setup the keyFile needed for the encrypt(String)/decrypt(String)
   * methods
   */
  @BeforeClass
  public static void setup() throws IOException {
    String tempPath = File.createTempFile("atti", "encryption").getParentFile().getCanonicalPath();
    
    File keyFile = new File(tempPath + "/" + KeyLoader.KEY_FILE_NAME);
    keyFile.deleteOnExit();
    
    File ivFile = new File(tempPath + "/" + KeyLoader.IV_FILE_NAME);
    ivFile.deleteOnExit();
    
    KeyLoader.generateKeyAndIVFile(keyFile,ivFile);
    System.setProperty(DefaultPasswordService.KEY_PATH_PROPERTY, tempPath);
  }

  @Test
  public void testGenerateKey() throws NoSuchAlgorithmException,
      NoSuchPaddingException, InvalidKeyException,
      InvalidAlgorithmParameterException {
    SecretKey generateKey = DefaultPasswordService.generateKey();
    IvParameterSpec generateIV = DefaultPasswordService.generateIV();
    Cipher instance = Cipher.getInstance(DefaultPasswordService.TRANSFORMATION);

    // would throw an exception if the key/iv are not valid for the
    // transformation
    instance.init(Cipher.ENCRYPT_MODE, generateKey, generateIV);
  }

  @Test
  public void testWithStoredKeys() throws Exception {
    String plainText = generateRandomText();
    String cipherText = DefaultPasswordService.encrypt(plainText);
    String decrypted = DefaultPasswordService.decrypt(cipherText);
    assertEquals(plainText, decrypted);
  }

  private String generateRandomText() {
    SecureRandom random;
    try {
      random = SecureRandom
          .getInstance(DefaultPasswordService.SECURE_RANDOM_ALG);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    int size = 150 + (int)(Math.random() * (100));
    byte[] bytes = new byte[size];
    random.nextBytes(bytes);
    String plainText = new String(bytes);
    return plainText;
  }

  @Test
  public void testWithGeneratedKeys() {
    String plaintext = generateRandomText();
    SecretKey key = DefaultPasswordService.generateKey();
    IvParameterSpec IV = DefaultPasswordService.generateIV();

    String encrypted = DefaultPasswordService.encrypt(plaintext, key, IV);
    String decrypted = DefaultPasswordService.decrypt(encrypted, key, IV);

    System.out.println();
    assertEquals(decrypted, plaintext);
  }

}
