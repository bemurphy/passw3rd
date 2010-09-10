package com.atti;

import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author nmatatall
 * 
 *         Create Placeholders in your configuration file to indicate that the
 *         property value needs to be retrieved from a password file. The value
 *         inside of the placeholder indicates which file will be decrypted. Do
 *         not specify the full/relative path to the file. By default it will
 *         check the users home directory, but you can change this by setting
 *         the {@link DefaultPasswordService#KEY_PATH_PROPERTY}.
 *         
 *         Currently no support for writing out encrypted properties so don't 
 *         build a properties object and write it out, the values will be in 
 *         plaintext. 
 */
public class EncryptedProperties extends Properties {

  /**
   * example usage: password = ${password_file}
   * 
   * This will set password to the decrypted value of the contents of
   * password_file
   */
  public static final Pattern PLACEHOLDER = Pattern.compile("\\$\\{.*\\}");

  @Override
  public String getProperty(String key) {
    String value = super.getProperty(key);
    if (value != null && PLACEHOLDER.matcher(value).matches()) {
      return DefaultPasswordService.getPassword(value.replaceAll("[\\$\\{\\}]", ""));
    }
    return value;
  }

  @Override
  public String getProperty(String key, String defaultValue) {
    String property = getProperty(key);
    return property == null ? defaultValue : property;
  }
}
