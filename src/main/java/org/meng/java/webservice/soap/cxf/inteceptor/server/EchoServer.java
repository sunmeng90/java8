package org.meng.java.webservice.soap.cxf.inteceptor.server;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.phase.Phase;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
        JaxWsServerFactoryBean factoryBean = new JaxWsServerFactoryBean();
        factoryBean.setServiceClass(EchoServer.class);
        factoryBean.setAddress("http://localhost:8080/echo");
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getInInterceptors().add(new AuthInteceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        Server server = factoryBean.create();
        server.start();
    }

}


