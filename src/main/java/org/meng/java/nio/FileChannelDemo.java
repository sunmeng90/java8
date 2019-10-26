package org.meng.java.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChannelDemo {

    public static void main(String[] args) throws IOException {
        demo1("C:\\Users\\meng\\Documents\\GitHub\\java\\src\\main\\resources\\data\\emp.csv", ".\\output\\emp1.csv");
        demo2("C:\\Users\\meng\\Documents\\GitHub\\java\\src\\main\\resources\\data\\emp.csv", ".\\output\\emp2.csv");
        demo3("C:\\Users\\meng\\Documents\\GitHub\\java\\src\\main\\resources\\data\\emp.csv", ".\\output\\emp3.csv");
        demo4("C:\\Users\\meng\\Documents\\GitHub\\java\\src\\main\\resources\\data\\emp.csv", ".\\output\\emp4.csv");
    }


    /**
     * Copy file using channel using non-direct buffer
     */
    public static void demo1(String src, String tgt) throws IOException {
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(tgt);

        ByteBuffer buf = ByteBuffer.allocate(1024);
        FileChannel inputChannel = fis.getChannel();
        FileChannel outputChannel = fos.getChannel();

        // Reads a sequence of bytes from this channel into the given buffer.
        while (inputChannel.read(buf) != -1) {
            buf.flip();//switch to read buffer mode
            //Writes a sequence of bytes to this channel from a subsequence of the given buffer
            outputChannel.write(buf);
            //Clears this buffer. The position is set to zero, the limit is set to the capacity, and the mark is discarded.
            //This method does not actually erase the data in the buffer
            buf.clear(); //Clears this buffer. The position is set to zero, the limit is set to the capacity, and the mark is discarded.
        }

        outputChannel.close();
        inputChannel.close();
        fos.close();
        fis.close();
    }

    /**
     * Copy file using channel using direct buffer(map)
     */
    public static void demo2(String src, String tgt) throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get(src), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get(tgt), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);

        //map file to memory
        MappedByteBuffer inMap = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMap = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        byte[] buf = new byte[inMap.limit()];
        inMap.get(buf);
        outMap.put(buf);
        inChannel.close();
        outChannel.close();
    }

    /**
     * Copy file using channel
     */
    public static void demo3(String src, String tgt) throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get(src), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get(tgt), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.close();
        outChannel.close();
    }

    /**
     * Copy file using scattering read and gathering write
     */
    public static void demo4(String src, String tgt) throws IOException {
        FileChannel inChannel = new RandomAccessFile(src, "r").getChannel();
        FileChannel outChannel = new RandomAccessFile(tgt, "rw").getChannel();
        ByteBuffer[] buffers = {
                ByteBuffer.allocate(100),
                ByteBuffer.allocate(200),
                ByteBuffer.allocate(100)
        };
        inChannel.read(buffers); //scattering read: read bytes from input file to buffers array
        for (int i = 0; i < buffers.length; i++) {
            buffers[i].flip();
        }
        outChannel.write(buffers);
        inChannel.close();
        outChannel.close();
    }
}
