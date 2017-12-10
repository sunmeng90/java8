package org.meng.java.webservice.soap.cxf.inteceptor.server;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class UserXMLAdapter extends XmlAdapter<StringUser, Map<Integer, User>> {

    @Override
    public Map<Integer, User> unmarshal(StringUser v) throws Exception {
        Map<Integer, User> users = new HashMap<>();
        for (StringUser.Entry entry : v.getEntries()) {
            users.put(entry.getKey(), entry.getValue());
        }
        return users;
    }

    @Override
    public StringUser marshal(Map<Integer, User> v) throws Exception {
        StringUser stringUser = new StringUser();
        for (Map.Entry<Integer, User> entry : v.entrySet()) {
            stringUser.getEntries().add(new StringUser.Entry(entry.getKey(), entry.getValue()));
        }
        return stringUser;
    }
}
