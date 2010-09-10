package com.atti;

import java.net.MalformedURLException;
import java.net.URL;

import com.strongauth.strongkeylite.web.Encryption;
import com.strongauth.strongkeylite.web.EncryptionService;
import com.strongauth.strongkeylite.web.StrongKeyLiteException_Exception;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class SimplePasswordClient {
  private long domainID;
  private String userName;
  private String servicePassword;
  private String endPoint;
  private Encryption port;
  
  public String storePassword(String plaintext) {
    init();
    try {
      return port.encrypt(domainID, userName,
          this.servicePassword, plaintext);
    } catch (StrongKeyLiteException_Exception e) {
      throw new RuntimeException("Couldn't store password in central server", e);
    }
  }
  
  public String retrievePassword(String token) {
    init();
    try {
      return port.decrypt(domainID, userName,
          this.servicePassword, token);
    } catch (StrongKeyLiteException_Exception e) {
      throw new RuntimeException("Couldn't retrieve password in central server", e);
    }
  }

  private void init() {
    if(this.port == null) {
      this.port = buildEncryptionPort();
    }
  }

  private Encryption buildEncryptionPort() {
    URL url;
    try {
      url = new URL(EncryptionService.class.getResource("."),
          endPoint);
    } catch (MalformedURLException e) {
      throw new RuntimeException("couldn't build enpoint url: " + endPoint, e);
    }

    return new EncryptionService(url).getEncryptionPort();
  }

  /** * @return Returns the domainID.
   */
  public long getDomainID() {
    return domainID;
  }

  /**
   * @param domainID The domainID to set.
   */
  public void setDomainID(long domainID) {
    this.domainID = domainID;
  }

  /** * @return Returns the userName.
   */
  public String getUserName() {
    return userName;
  }

  /**
   * @param userName The userName to set.
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  /** * @return Returns the endPoint.
   */
  public String getEndPoint() {
    return endPoint;
  }

  /**
   * @param endPoint The endPoint to set.
   */
  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  /**
   * @param port The port to set.
   */
  public void setPort(Encryption port) {
    this.port = port;
  }

  /**
   * @param password the password for the underlying webservice
   */
  public void setServicePassword(String password) {
    this.servicePassword = password;
  }
}
