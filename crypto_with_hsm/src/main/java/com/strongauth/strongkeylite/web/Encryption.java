package com.strongauth.strongkeylite.web;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.2.9
 * Mon Jun 14 16:10:14 PDT 2010
 * Generated source version: 2.2.9
 * 
 */
 
@WebService(targetNamespace = "http://web.strongkeylite.strongauth.com/", name = "Encryption")
@XmlSeeAlso({ObjectFactory.class})
public interface Encryption {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "encrypt", targetNamespace = "http://web.strongkeylite.strongauth.com/", className = "com.strongauth.strongkeylite.web.Encrypt")
    @WebMethod
    @ResponseWrapper(localName = "encryptResponse", targetNamespace = "http://web.strongkeylite.strongauth.com/", className = "com.strongauth.strongkeylite.web.EncryptResponse")
    public java.lang.String encrypt(
        @WebParam(name = "did", targetNamespace = "")
        java.lang.Long did,
        @WebParam(name = "username", targetNamespace = "")
        java.lang.String username,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password,
        @WebParam(name = "plaintext", targetNamespace = "")
        java.lang.String plaintext
    ) throws StrongKeyLiteException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "delete", targetNamespace = "http://web.strongkeylite.strongauth.com/", className = "com.strongauth.strongkeylite.web.Delete")
    @WebMethod
    @ResponseWrapper(localName = "deleteResponse", targetNamespace = "http://web.strongkeylite.strongauth.com/", className = "com.strongauth.strongkeylite.web.DeleteResponse")
    public java.lang.Boolean delete(
        @WebParam(name = "did", targetNamespace = "")
        java.lang.Long did,
        @WebParam(name = "username", targetNamespace = "")
        java.lang.String username,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password,
        @WebParam(name = "token", targetNamespace = "")
        java.lang.String token
    ) throws StrongKeyLiteException_Exception;

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "decrypt", targetNamespace = "http://web.strongkeylite.strongauth.com/", className = "com.strongauth.strongkeylite.web.Decrypt")
    @WebMethod
    @ResponseWrapper(localName = "decryptResponse", targetNamespace = "http://web.strongkeylite.strongauth.com/", className = "com.strongauth.strongkeylite.web.DecryptResponse")
    public java.lang.String decrypt(
        @WebParam(name = "did", targetNamespace = "")
        java.lang.Long did,
        @WebParam(name = "username", targetNamespace = "")
        java.lang.String username,
        @WebParam(name = "password", targetNamespace = "")
        java.lang.String password,
        @WebParam(name = "token", targetNamespace = "")
        java.lang.String token
    ) throws StrongKeyLiteException_Exception;
}
