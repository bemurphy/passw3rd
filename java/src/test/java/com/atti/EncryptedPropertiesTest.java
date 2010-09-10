package com.atti;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

public class EncryptedPropertiesTest {
  public static final String PASSWORD = "oreoshake";
  static Properties props = new EncryptedProperties();
  
  @BeforeClass
  public static void setup() throws FileNotFoundException, IOException {
    props.load(new FileReader("src/test/resources/test.properties"));
    System.setProperty(DefaultPasswordService.KEY_PATH_PROPERTY, "src/test/resources");
  }

  @Test
  public void testGetPropertyString() {
    assertEquals(PASSWORD, props.getProperty("password"));
  }

  @Test
  public void testGetPropertyStringString() {
    assertEquals(PASSWORD, props.getProperty("password", PASSWORD));
    assertEquals(PASSWORD, props.getProperty("passwordthatdoesn'texist", PASSWORD));
  }
}
