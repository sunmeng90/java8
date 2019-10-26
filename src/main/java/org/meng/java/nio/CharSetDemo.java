package org.meng.java.nio;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CharSetDemo {
    public static void main(String[] args) throws CharacterCodingException {
//        printCharsets();
        encodeAndDecode();
    }
    public static void printCharsets() {
        Charset.availableCharsets().forEach((k, v) -> {
            System.out.println(k + " => " + v);
        });
    }

    public static void encodeAndDecode() throws CharacterCodingException {
        Charset charset = Charset.forName("GBK");
        String str = "中国";
        CharsetEncoder charsetEncoder = charset.newEncoder();
        CharsetDecoder charsetDecoder = charset.newDecoder();
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put(str);
        charBuffer.flip();
        //encode returns A newly-allocated byte buffer containing the result of the encoding operation. The buffer's position will be zero and its limit will follow the last byte written.
        ByteBuffer byteBuffer = charsetEncoder.encode(charBuffer); //encoding as GBK
        System.out.println(byteBuffer);// 4 bytes for two 中国 using GBK
//        byteBuffer.flip();//??? no need
        System.out.println(byteBuffer);// 4 bytes for two 中国 using GBK
        //note: encode and decode will change position value, use it carefully ??? really
        CharBuffer decodeCharBuffer = charsetDecoder.decode(byteBuffer);
        System.out.println(decodeCharBuffer);

    }
}
