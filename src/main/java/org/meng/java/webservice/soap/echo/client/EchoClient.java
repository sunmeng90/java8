package org.meng.java.webservice.soap.echo.client;


import org.meng.java.webservice.soap.echo.client.generated.EchoServerService;
import org.meng.java.webservice.soap.echo.client.generated.StringUser;

public class EchoClient{
    public static void main(String[]args){
        EchoServerService echoServerService = new EchoServerService();
        org.meng.java.webservice.soap.echo.client.generated.EchoServer  echoServer = echoServerService.getEchoServerPort();
        System.out.println(echoServer.echo("meng"));
        echoServer.getAllUsers().stream().map(user -> user.getId()+": "+user.getName()).forEach(System.out::println);
        StringUser stringUser = echoServer.getUsersMap();
        stringUser.getEntries().stream().map(entry -> entry.getValue().getId()+"=> "+entry.getValue().getName()).forEach(System.out::println);
    }
}