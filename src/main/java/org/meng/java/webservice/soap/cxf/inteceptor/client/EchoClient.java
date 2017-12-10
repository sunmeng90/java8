package org.meng.java.webservice.soap.cxf.inteceptor.client;


import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.meng.java.webservice.soap.cxf.inteceptor.client.generated.EchoServer;
import org.meng.java.webservice.soap.cxf.inteceptor.client.generated.StringUser;
import org.meng.java.webservice.soap.cxf.inteceptor.server.AuthInteceptor;

public class EchoClient {
    public static void main(String[] args) {
        JaxWsProxyFactoryBean factoryBean  = new JaxWsProxyFactoryBean();
        factoryBean.setServiceClass(EchoServer.class);
        factoryBean.setAddress("http://localhost:8080/echo");
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new AddAuthInteceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        EchoServer echoServer = factoryBean.create(EchoServer.class);
        System.out.println(echoServer.echo("meng"));
        echoServer.getAllUsers().stream().map(user -> user.getId() + ": " + user.getName()).forEach(System.out::println);
        StringUser stringUser = echoServer.getUsersMap();
        stringUser.getEntries().stream().map(entry -> entry.getValue().getId() + "=> " + entry.getValue().getName()).forEach(System.out::println);
    }
}