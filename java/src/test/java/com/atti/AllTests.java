package com.atti;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
 
@RunWith(Suite.class)
@Suite.SuiteClasses({
  DefaultPasswordServiceTest.class,
  EncryptedPropertiesTest.class,
  SpringConfigurerTest.class 
})
public class AllTests {
    // why on earth I need this class, I have no idea! 
}
