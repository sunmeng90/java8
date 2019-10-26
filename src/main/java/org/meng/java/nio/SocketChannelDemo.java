package org.meng.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class SocketChannelDemo {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            try {
                server();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(500);
                    client();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.currentThread().join();
    }

    public static void client() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(8009));
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        CharsetEncoder encoder = Charset.forName("utf-8").newEncoder();
        charBuffer.put("hello");
        charBuffer.flip();
        System.out.println("sending " + charBuffer);
        ByteBuffer byteBuffer = encoder.encode(charBuffer);
        socketChannel.write(byteBuffer);//write bytes from buffer to channel
        charBuffer.clear();
        socketChannel.close();
    }

    public static void server() throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8009));
        CharsetDecoder decoder = Charset.forName("utf-8").newDecoder();
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println("got new connection");
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (socketChannel.read(byteBuffer) != -1) {//read bytes from channel to buffer
                byteBuffer.flip();
                System.out.println("got " + decoder.decode(byteBuffer));
                byteBuffer.clear();
            }
            socketChannel.close();
            Thread.sleep(1000);
        }
    }
}


