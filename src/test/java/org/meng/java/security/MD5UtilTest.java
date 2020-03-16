package org.meng.java.security;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MD5UtilTest {

    @Test
    void md5Digest() throws NoSuchAlgorithmException {
        assertEquals("fc5e038d38a57032085441e7fe7010b0", MD5Util.md5Digest("helloworld"));
    }
}