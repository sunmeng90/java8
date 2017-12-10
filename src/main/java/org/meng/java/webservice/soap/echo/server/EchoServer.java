package org.meng.java.webservice.soap.echo.server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebService
public class EchoServer {
    @WebMethod
    public String echo(String name) {
        String greeting = "Hi, " + name;
        return greeting;
    }

    @WebMethod
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "meng"));
        users.add(new User(2, "sam"));
        users.add(new User(3, "someone"));
        return users;
    }

    @WebMethod
    @XmlJavaTypeAdapter(UserXMLAdapter.class)
    public Map<Integer, User> getUsersMap() {
        Map<Integer, User> userMap = new HashMap<>();
        userMap.put(1, new User(1, "meng"));
        userMap.put(2, new User(2, "sam"));
        userMap.put(3, new User(3, "someone"));
        return userMap;
    }

    public static void main(String[] args) {
        EchoServer echoServer = new EchoServer();
        Endpoint.publish("http://localhost:8080/echo", echoServer);
    }

}


