package org.meng.java.webservice.soap.cxf.inteceptor.client;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import java.util.List;

public class AddAuthInteceptor extends AbstractPhaseInterceptor<SoapMessage> {
    public AddAuthInteceptor() {
        super(Phase.PREPARE_SEND);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        List<Header> headerList = message.getHeaders();
        Document doc = DOMUtils.createDocument();
        Element auth = doc.createElementNS("http://www.webservicedemo.com/auth", "auth");
        Element name = doc.createElement("name");
        name.setTextContent("webservicedemo");
        Element password = doc.createElement("password");
        password.setTextContent("webservicedemo");
        auth.appendChild(name);
        auth.appendChild(password);
        headerList.add(new Header(new QName(""), auth));
    }
}
