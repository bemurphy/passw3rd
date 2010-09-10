package com.atti;

import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PasswordConfigurer extends PropertyPlaceholderConfigurer {
  /**
   * Takes the placeholder and returns the password in the file with the same
   * name as the placeholder
   * 
   * @param placeholder
   * @return
   */
  protected String resolvePlaceholder(final String placeholder) {
    return DefaultPasswordService.getPassword(placeholder);
  }

  @Override
  protected String resolvePlaceholder(final String placeholder,
      final Properties props) {
    return resolvePlaceholder(placeholder);
  }
}
