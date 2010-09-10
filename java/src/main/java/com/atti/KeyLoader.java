package com.atti;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

/**
 * Store key as:
 * 
 * iv key
 * 
 * @author nmatatall
 * 
 */
public class KeyLoader {
  public static final String KEY_FILE_NAME = ".atti-pw-encryptionKey";
  public static final String IV_FILE_NAME = ".atti-pw-encryptionIV";
  public static final String DEFAULT_LOCATION = System.getProperty("user.home")
      + "/";
  private static final Logger log = Logger.getLogger(KeyLoader.class);

  public static SecretKeyWithIV load() {
    return load(System.getProperty(DefaultPasswordService.KEY_PATH_PROPERTY));
  }

  public static SecretKeyWithIV load(String path) {
    String key;
    String IV;
    if (path == null) {
      path = DEFAULT_LOCATION;
    }

    if (!path.endsWith(File.separator)) {
      path += File.separator;
    }

    try {
      log.info("Loading key and IV from dir: " + path);

      File file = new File(path + KEY_FILE_NAME);
      BufferedReader reader = new BufferedReader(new FileReader(file));
      key = reader.readLine().trim();
      reader.close();

      reader = new BufferedReader(new FileReader(new File(path + IV_FILE_NAME)));
      IV = reader.readLine().trim();
      reader.close();
    } catch (FileNotFoundException e) {
      log.error("Could not find key file", e);
      throw new RuntimeException("Could not find key file", e);
    } catch (IOException e) {
      log.error("Could not read key file", e);
      throw new RuntimeException("Could not read key file", e);
    }
    Hex hex = new Hex();
    byte[] ivBytes;
    byte[] decodedBytes;
    try {
      decodedBytes = hex.decode(key.getBytes());
      ivBytes = hex.decode(IV.getBytes());
    } catch (DecoderException e) {
      throw new RuntimeException("Couldn't read key/iv", e);
    }
    
    return new SecretKeyWithIV(decodedBytes, ivBytes);
  }

  /**
   * Generate key and IV files using all defaults
   */
  public static void generateKeyAndIVFile() {
    generateKeyAndIVFile(null);
  }

  /**
   * Generates a key/IV and writes the values in separate files, Hex encoded
   * 
   * @param outputPath
   *          directory where files will be written to
   */
  public static void generateKeyAndIVFile(String outputPath) {
    if (outputPath == null) {
      outputPath = DEFAULT_LOCATION;
    }

    generateKeyAndIVFile(new File(outputPath + KEY_FILE_NAME), new File(
        outputPath + IV_FILE_NAME));
  }

  /**
   * Generates a key and IV and puts them in the respective files.
   */
  public static void generateKeyAndIVFile(File keyFile, File ivFile) {
    SecretKey key = DefaultPasswordService.generateKey();
    IvParameterSpec iv = DefaultPasswordService.generateIV();

    Hex hex = new Hex();
    byte[] hexKey = hex.encode(key.getEncoded());
    byte[] hexIV = hex.encode(iv.getIV());

    FileOutputStream file;
    try {
      file = new FileOutputStream(ivFile);
      file.write(hexIV);
      file.flush();
      file.close();
      file = new FileOutputStream(keyFile);
      file.write(hexKey);
      file.flush();
      file.close();
      log.info("Generated new keys in " + keyFile.getParent());
    } catch (IOException e) {
      log.error("Could not write key file", e);
      throw new RuntimeException("Couldn't write key file", e);
    }
    
    
  }
}
