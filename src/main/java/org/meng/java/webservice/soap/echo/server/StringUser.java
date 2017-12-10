package org.meng.java.webservice.soap.echo.server;

import java.util.ArrayList;
import java.util.List;

public class StringUser {

    private List<Entry> entries;

    public StringUser() {
        this.entries = new ArrayList<Entry>();
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    static class Entry {
        Integer key;
        User value;

        public Entry(Integer key, User value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public User getValue() {
            return value;
        }

        public void setValue(User value) {
            this.value = value;
        }
    }
}
