
package org.meng.java.webservice.soap.echo.client.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.meng.java.webservice.soap.echo.client.generated package. 
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

    private final static QName _GetAllUsersResponse_QNAME = new QName("http://server.echo.soap.webservice.java.meng.org/", "getAllUsersResponse");
    private final static QName _GetUsersMapResponse_QNAME = new QName("http://server.echo.soap.webservice.java.meng.org/", "getUsersMapResponse");
    private final static QName _GetAllUsers_QNAME = new QName("http://server.echo.soap.webservice.java.meng.org/", "getAllUsers");
    private final static QName _Echo_QNAME = new QName("http://server.echo.soap.webservice.java.meng.org/", "echo");
    private final static QName _GetUsersMap_QNAME = new QName("http://server.echo.soap.webservice.java.meng.org/", "getUsersMap");
    private final static QName _EchoResponse_QNAME = new QName("http://server.echo.soap.webservice.java.meng.org/", "echoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.meng.java.webservice.soap.echo.client.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAllUsers }
     * 
     */
    public GetAllUsers createGetAllUsers() {
        return new GetAllUsers();
    }

    /**
     * Create an instance of {@link GetAllUsersResponse }
     * 
     */
    public GetAllUsersResponse createGetAllUsersResponse() {
        return new GetAllUsersResponse();
    }

    /**
     * Create an instance of {@link GetUsersMapResponse }
     * 
     */
    public GetUsersMapResponse createGetUsersMapResponse() {
        return new GetUsersMapResponse();
    }

    /**
     * Create an instance of {@link Echo }
     * 
     */
    public Echo createEcho() {
        return new Echo();
    }

    /**
     * Create an instance of {@link EchoResponse }
     * 
     */
    public EchoResponse createEchoResponse() {
        return new EchoResponse();
    }

    /**
     * Create an instance of {@link GetUsersMap }
     * 
     */
    public GetUsersMap createGetUsersMap() {
        return new GetUsersMap();
    }

    /**
     * Create an instance of {@link StringUser }
     * 
     */
    public StringUser createStringUser() {
        return new StringUser();
    }

    /**
     * Create an instance of {@link Entry }
     * 
     */
    public Entry createEntry() {
        return new Entry();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllUsersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.echo.soap.webservice.java.meng.org/", name = "getAllUsersResponse")
    public JAXBElement<GetAllUsersResponse> createGetAllUsersResponse(GetAllUsersResponse value) {
        return new JAXBElement<GetAllUsersResponse>(_GetAllUsersResponse_QNAME, GetAllUsersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersMapResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.echo.soap.webservice.java.meng.org/", name = "getUsersMapResponse")
    public JAXBElement<GetUsersMapResponse> createGetUsersMapResponse(GetUsersMapResponse value) {
        return new JAXBElement<GetUsersMapResponse>(_GetUsersMapResponse_QNAME, GetUsersMapResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllUsers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.echo.soap.webservice.java.meng.org/", name = "getAllUsers")
    public JAXBElement<GetAllUsers> createGetAllUsers(GetAllUsers value) {
        return new JAXBElement<GetAllUsers>(_GetAllUsers_QNAME, GetAllUsers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Echo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.echo.soap.webservice.java.meng.org/", name = "echo")
    public JAXBElement<Echo> createEcho(Echo value) {
        return new JAXBElement<Echo>(_Echo_QNAME, Echo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersMap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.echo.soap.webservice.java.meng.org/", name = "getUsersMap")
    public JAXBElement<GetUsersMap> createGetUsersMap(GetUsersMap value) {
        return new JAXBElement<GetUsersMap>(_GetUsersMap_QNAME, GetUsersMap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EchoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.echo.soap.webservice.java.meng.org/", name = "echoResponse")
    public JAXBElement<EchoResponse> createEchoResponse(EchoResponse value) {
        return new JAXBElement<EchoResponse>(_EchoResponse_QNAME, EchoResponse.class, null, value);
    }

}
