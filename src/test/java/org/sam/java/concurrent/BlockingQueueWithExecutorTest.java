package org.sam.java.concurrent;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by meng_ on 7/2/2017.
 */
public class BlockingQueueWithExecutorTest {

    @Test
    public void testGetFiles(){
        List<File> files = BlockingQueueWithExecutor.getFiles(new File("C:\\Users\\meng_\\Documents\\GitHub\\java8\\src\\"));

        Assert.assertNotNull(files);
        Assert.assertTrue(files.size()>0);
    }

    @Test
    public void testWordCount(){
        File tempFile = null;
        try {
            tempFile = File.createTempFile("testWordCount", "txt");
            FileWriter fw = new FileWriter(tempFile);
            fw.write("hello world hello world hello Meng");
            fw.flush();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Integer> resMap = BlockingQueueWithExecutor.wordCount(tempFile);
        Assert.assertTrue(resMap != null);
        Assert.assertEquals("3 hello", "3", resMap.get("hello").toString());
    }

    @Test
    public void testPublish(){
        File tempFile = null;
        try {
            tempFile = File.createTempFile("testWordCount", "txt");
            FileWriter fw = new FileWriter(tempFile);
            fw.write("hello world hello world hello Meng");
            fw.flush();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Future<Map<String, Integer>>> futures = new BlockingQueueWithExecutor(tempFile).publish();
        Assert.assertTrue(futures.size()>0);
        try {
            Map<String, Integer> resMap = futures.get(0).get();
            Assert.assertEquals("3 hello", "3", resMap.get("hello").toString());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testExecute(){
        BlockingQueueWithExecutor blockingQueueWithExecutor = new BlockingQueueWithExecutor(new File("C:\\Users\\meng_\\Documents\\GitHub\\"));
        blockingQueueWithExecutor.execute();
    }

}
