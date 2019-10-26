package org.meng.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.time.LocalDateTime;
import java.util.Iterator;

public class SocketChannelNonBlockingDemo {

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
                    Thread.sleep(3000);
                    client();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5000);
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
        //1. get channel
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8009));
        //2. switch to non-blocking mode
        socketChannel.configureBlocking(false);
        //3. allocate a buffer
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        // prepare data
        CharsetEncoder encoder = Charset.forName("utf-8").newEncoder();
        charBuffer.put("hello " + Thread.currentThread().getName() + " " + LocalDateTime.now());
        charBuffer.flip();
        System.out.println("sending " + charBuffer);
        ByteBuffer byteBuffer = encoder.encode(charBuffer);
        //4. send to server
        socketChannel.write(byteBuffer);//write bytes from buffer to channel
        charBuffer.clear();
        socketChannel.close();
    }

    public static void server() throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //set channel as non-blocking
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8009));
        //get a selector
        Selector selector = Selector.open();
        //register a channel to a selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //loop to get ready channel
        while (true) {
            int select = selector.select();
            if (select > 0) {
                //if any channel are ready, then get all the ready channels
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    //check the ready event
                    if (selectionKey.readyOps() == SelectionKey.OP_ACCEPT) {
                        System.out.println("got new connection at "+ LocalDateTime.now());
                        //process the channel data
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.readyOps() == SelectionKey.OP_READ) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                        CharsetDecoder decoder = Charset.forName("utf-8").newDecoder();

                        while (socketChannel.read(byteBuffer) != -1) {//read bytes from channel to buffer
                            byteBuffer.flip();
                            System.out.println("got " + decoder.decode(byteBuffer));
                            byteBuffer.clear();
                        }
                    }
                    iterator.remove(); // remove the selected event that are already processed
                }
            }
        }
    }
}


