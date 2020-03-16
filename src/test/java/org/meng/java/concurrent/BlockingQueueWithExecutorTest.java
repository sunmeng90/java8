package org.meng.java.concurrent;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by meng_ on 7/2/2017.
 */
public class BlockingQueueWithExecutorTest {

    @Test
    public void testGetFiles() {
        List<File> files = BlockingQueueWithExecutor.getFiles(new File(".\\src"));

        assertNotNull(files);
        assertTrue(files.size() > 0);
    }

    @Test
    public void testWordCount() {
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
        assertTrue(resMap != null);
        assertEquals("3", resMap.get("hello").toString());
    }

    @Test
    public void testExecute() {
        BlockingQueueWithExecutor blockingQueueWithExecutor = new BlockingQueueWithExecutor(new File(".\\src"));
        blockingQueueWithExecutor.execute();
    }

}
