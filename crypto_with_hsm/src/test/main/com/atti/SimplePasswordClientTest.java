package com.atti;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimplePasswordClientTest {
  private String password;

  @BeforeClass
  public static void setupBeforeClass() {
    System.setProperty("javax.net.ssl.trustStore", "src/test/resources/keystore");
    System.setProperty("javax.net.ssl.trustStorePassword", "changeme");
    System.setProperty(DefaultPasswordService.KEY_PATH_PROPERTY, "src/test/resources");
    System.setProperty(DefaultPasswordService.PASSWORD_FILE_DIRECTORY, "src/test/resources");
  }

  @Before
  public void setup() {
    this.password = generateRandomText();
  }
  
  @Test
  public void testManual() {
    SimplePasswordClient client = new SimplePasswordClient();
    client.setUserName("encryptdecrypt");
    client.setServicePassword(DefaultPasswordService.getPassword("encryptdecrypt"));
    client.setEndPoint("https://demo.strongauth.com:8181/strongkeyliteWAR/EncryptionService?wsdl");
    client.setDomainID(2);
    
    cryptoRoundTrip(client);
  }

  @Test
  public void testWithProperties() throws FileNotFoundException, IOException {
    SimplePasswordClient client = new SimplePasswordClient();
    EncryptedProperties props = new EncryptedProperties();
    props.load(new FileReader("src/test/resources/test.properties"));
    client.setDomainID(Long.parseLong(props.getProperty("domain.id")));
    client.setServicePassword(props.getProperty("service.password"));
    client.setUserName(props.getProperty("user.name"));
    client.setEndPoint(props.getProperty("ws.endpoint"));
    
    cryptoRoundTrip(client); 
  }

  private void cryptoRoundTrip(SimplePasswordClient client) {
    String token = client.storePassword(password);
    System.out.println(token);
    String retrievedPassword = client.retrievePassword(token);
    assertEquals(password, retrievedPassword);
  }
  
  @Test 
  public void testSpring() {
    ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
    SimplePasswordClient client = (SimplePasswordClient) context.getBean("client");
    
    cryptoRoundTrip(client);
  }

  private String generateRandomText() {
    SecureRandom random;
    try {
      random = SecureRandom
          .getInstance(DefaultPasswordService.SECURE_RANDOM_ALG);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
    int size = 450 + (int)(Math.random() * (1000));
    byte[] bytes = new byte[size];
    random.nextBytes(bytes);
    String plainText = new String(bytes);
    // can only store alphanumerics at this point :(
    return plainText.replaceAll("[^\\w]", "");
  }
}
