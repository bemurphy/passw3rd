package com.atti;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SpringConfigurerTest {
  private static ApplicationContext factory;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    System.setProperty(DefaultPasswordService.KEY_PATH_PROPERTY, "src/test/resources");
    factory = new ClassPathXmlApplicationContext("spring-beans.xml");
  }
  
  @Test
  public void testTranslation() {
    String password = (String) factory.getBean("password");
    assertEquals(EncryptedPropertiesTest.PASSWORD, password);
  }

}
