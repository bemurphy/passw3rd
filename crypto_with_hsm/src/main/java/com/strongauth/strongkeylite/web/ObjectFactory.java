
package com.strongauth.strongkeylite.web;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.strongauth.strongkeylite.web package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Delete_QNAME = new QName("http://web.strongkeylite.strongauth.com/", "delete");
    private final static QName _DecryptResponse_QNAME = new QName("http://web.strongkeylite.strongauth.com/", "decryptResponse");
    private final static QName _DeleteResponse_QNAME = new QName("http://web.strongkeylite.strongauth.com/", "deleteResponse");
    private final static QName _StrongKeyLiteException_QNAME = new QName("http://web.strongkeylite.strongauth.com/", "StrongKeyLiteException");
    private final static QName _Decrypt_QNAME = new QName("http://web.strongkeylite.strongauth.com/", "decrypt");
    private final static QName _EncryptResponse_QNAME = new QName("http://web.strongkeylite.strongauth.com/", "encryptResponse");
    private final static QName _Encrypt_QNAME = new QName("http://web.strongkeylite.strongauth.com/", "encrypt");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.strongauth.strongkeylite.web
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EncryptResponse }
     * 
     */
    public EncryptResponse createEncryptResponse() {
        return new EncryptResponse();
    }

    /**
     * Create an instance of {@link DecryptResponse }
     * 
     */
    public DecryptResponse createDecryptResponse() {
        return new DecryptResponse();
    }

    /**
     * Create an instance of {@link DeleteResponse }
     * 
     */
    public DeleteResponse createDeleteResponse() {
        return new DeleteResponse();
    }

    /**
     * Create an instance of {@link Delete }
     * 
     */
    public Delete createDelete() {
        return new Delete();
    }

    /**
     * Create an instance of {@link Decrypt }
     * 
     */
    public Decrypt createDecrypt() {
        return new Decrypt();
    }

    /**
     * Create an instance of {@link Encrypt }
     * 
     */
    public Encrypt createEncrypt() {
        return new Encrypt();
    }

    /**
     * Create an instance of {@link StrongKeyLiteException }
     * 
     */
    public StrongKeyLiteException createStrongKeyLiteException() {
        return new StrongKeyLiteException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Delete }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.strongkeylite.strongauth.com/", name = "delete")
    public JAXBElement<Delete> createDelete(Delete value) {
        return new JAXBElement<Delete>(_Delete_QNAME, Delete.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DecryptResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.strongkeylite.strongauth.com/", name = "decryptResponse")
    public JAXBElement<DecryptResponse> createDecryptResponse(DecryptResponse value) {
        return new JAXBElement<DecryptResponse>(_DecryptResponse_QNAME, DecryptResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.strongkeylite.strongauth.com/", name = "deleteResponse")
    public JAXBElement<DeleteResponse> createDeleteResponse(DeleteResponse value) {
        return new JAXBElement<DeleteResponse>(_DeleteResponse_QNAME, DeleteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StrongKeyLiteException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.strongkeylite.strongauth.com/", name = "StrongKeyLiteException")
    public JAXBElement<StrongKeyLiteException> createStrongKeyLiteException(StrongKeyLiteException value) {
        return new JAXBElement<StrongKeyLiteException>(_StrongKeyLiteException_QNAME, StrongKeyLiteException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Decrypt }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.strongkeylite.strongauth.com/", name = "decrypt")
    public JAXBElement<Decrypt> createDecrypt(Decrypt value) {
        return new JAXBElement<Decrypt>(_Decrypt_QNAME, Decrypt.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EncryptResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.strongkeylite.strongauth.com/", name = "encryptResponse")
    public JAXBElement<EncryptResponse> createEncryptResponse(EncryptResponse value) {
        return new JAXBElement<EncryptResponse>(_EncryptResponse_QNAME, EncryptResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Encrypt }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.strongkeylite.strongauth.com/", name = "encrypt")
    public JAXBElement<Encrypt> createEncrypt(Encrypt value) {
        return new JAXBElement<Encrypt>(_Encrypt_QNAME, Encrypt.class, null, value);
    }

}
