package org.meng.java.nio;

import java.nio.ByteBuffer;

public class ByteBufferDemo {
    public static void main(String[] args) {
        String greeting = "hello world !";
        ByteBuffer bb = ByteBuffer.allocate(1024);
        System.out.println("--------before put(fill): position=0, limit=capacity=1024---------");
        System.out.println(bb);
        bb.put(greeting.getBytes());
        System.out.println("--------after put(fill), move position: position=13, limit=capacity=1024---------");
        System.out.println(bb);
        bb.flip();
        System.out.println("--------after flip to read(drain) mode, set position=0, limit=13, capacity=1024, read from 0 and maximum up to 13---------");
        System.out.println(bb);
        byte[] helloBytes = new byte[bb.limit()];
        bb.get(helloBytes);
        System.out.println("--------after read, position move to 13, : position=limit=13, capacity=1024---------");
        System.out.println(bb);
        System.out.println(new String(helloBytes));
        bb.rewind();
        System.out.println("--------after rewind, position move to 0: position=0, limit=13, capacity=1024---------");
        System.out.println(bb);
        bb.clear();
        System.out.println("--------after clear, position move to 0, the init status: position=0, limit=capacity=1024, but the bytes in buffer are not cleared, and the byte length is unknown---------");
        System.out.println(bb);

        System.out.println("==============reset=============");
        //mark: mark the position value A, then reset to the mark position A
        ByteBuffer bb1 = ByteBuffer.allocate(1024);
        bb1.put(greeting.getBytes());
        System.out.println(bb1);
        bb1.flip();
        byte[] helloBytes1 = new byte[bb.limit()];
        bb1.get(helloBytes1, 0, 2);
        System.out.println("read first two bytes: " + new String(helloBytes1, 0, 2));
        System.out.println(bb1);
        bb1.mark();//mark the current position 2
        System.out.println("mark the current position");
        System.out.println(bb1);
        bb1.get(helloBytes1, 2, 2);//offset: The offset within the array(helloBytes1) of the first byte to be written
        System.out.println("read next two bytes(third, fourth): " + new String(helloBytes1, 0, 4));
        System.out.println(bb1);
        bb1.reset(); //reset position to previous marked value 2
        System.out.println("reset to marked position");
        System.out.println(bb1);
        System.out.println("has remaining: " + bb1.hasRemaining());//Tells whether there are any elements between the current position and the limit
        System.out.println("get the remaining size(position->limit)" + bb1.remaining()); //Returns the number of elements between the current position and the limit
        //0 <= mark <= position <= limit <= capacity

        //non-direct buffer: allocate() in JVM heap, and is backed by a java array
        //direct buffer: allocateDirect() ? expensive
    }
}
