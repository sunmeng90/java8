package org.meng.java.webservice.soap.cxf.inteceptor.server;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.namespace.QName;
import java.util.List;

public class AuthInteceptor extends AbstractPhaseInterceptor<SoapMessage> {

    public AuthInteceptor() {
        super(Phase.PRE_INVOKE);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        List<Header> headers = message.getHeaders();
        if (headers == null || headers.size() < 1) {
            throw new Fault(new Exception("No headers provided"));
        }

        Element auth = null;
        for (Header header : headers) {
            QName qname = header.getName();
            String ns = qname.getNamespaceURI();
            String tag = qname.getLocalPart();
            if(ns !=null && ns.equals("http://www.webservicedemo.com/auth") && tag !=null && tag.equals("auth")){
                auth = (Element) header.getObject();
                break;
            }
        }

        if(auth == null){
            throw new Fault(new Exception("No Auth information"));
        }

        NodeList nameList = auth.getElementsByTagName("name");
        NodeList pwdList = auth.getElementsByTagName("password");

        if(nameList.getLength() != 1 || pwdList.getLength() !=1){
            throw new Fault(new Exception("Invalid Auth information"));
        }

        String name = nameList.item(0).getTextContent();
        String password = pwdList.item(0).getTextContent();

        if(!"webservicedemo".equals(name) || !"webservicedemo".equals(password)){
            throw new Fault(new Exception("Wrong Auth information"));
        }
    }
}
