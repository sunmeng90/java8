package org.sam.java.concurrent;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by meng_ on 7/2/2017.
 */
public class BlockingQueueWithExecutor {

    private BlockingQueue<File> fileQueue = new LinkedBlockingQueue<File>();
    private BlockingQueue<Map<String, Integer>> wordCountQueue = new LinkedBlockingQueue<Map<String, Integer>>();
    private static final int PERSIST_THREAD_THREAD = 5;
    private ExecutorService wordCountThreadPool = Executors.newFixedThreadPool(5);
    private ExecutorService persistenceThreadPool = Executors.newFixedThreadPool(5);

    private AtomicBoolean populateWordQueueFinished = new AtomicBoolean(false);
    private File baseDir;

    public BlockingQueueWithExecutor(File baseDir) {
        this.baseDir = baseDir;
    }


    public static List<File> getFiles(File file) {
        List<File> res = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File entry : files) {
                if (entry.isFile() && entry.getName().endsWith(".java")) {
                    res.add(entry);
                } else if (entry.isDirectory()) {
                    res.addAll(getFiles(entry));
                }
            }
        } else {
            res.add(file);
        }
        return res;
    }

    public List<Future<Map<String, Integer>>> publish() {
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();
        while (!fileQueue.isEmpty()) {
            File file = fileQueue.poll();
            if (file != null) {
                futures.add(wordCountThreadPool.submit(new Callable<Map<String, Integer>>() {
                    @Override
                    public Map<String, Integer> call() throws Exception {
                        Map<String, Integer> resMap = wordCount(file);
                        //populate word map to word count queue
                        BlockingQueueWithExecutor.this.wordCountQueue.put(resMap);
                        return resMap;
                    }
                }));
            }
        }
        return futures;
    }

    public List<Future<Void>> consume() {
        List<Future<Void>> futures = new ArrayList<>();
        for (int i = 0; i < PERSIST_THREAD_THREAD; i++) {
            futures.add(this.persistenceThreadPool.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {

                    while (true) {
                        if (BlockingQueueWithExecutor.this.wordCountQueue.isEmpty() && BlockingQueueWithExecutor.this.populateWordQueueFinished.get()) {
                            break;
                        }
                        Map<String, Integer> res = BlockingQueueWithExecutor.this.wordCountQueue.poll();
                        if (res != null) {
                            BlockingQueueWithExecutor.this.persist(res);
                        }
                    }
                    return null;
                }
            }));

        }
        return futures;
    }

    private void persist(Map<String, Integer> res) {
        System.out.println("Saving word count map: " + res);
    }

    public static Map<String, Integer> wordCount(File file) {
        Map<String, Integer> resMap = new HashMap<>();
        if (file.isDirectory()) {
            return resMap;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (resMap.get(word) == null) {
                        resMap.put(word, 1);
                    } else {
                        resMap.put(word, resMap.get(word) + 1);
                    }
                }
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resMap;
    }

    public void execute(){
        this.fileQueue.addAll(getFiles(baseDir));
        System.out.println("Total Number of file is :"+ fileQueue.size());

        List<Future<Map<String, Integer>>> wordCountMapList = this.publish();
        List<Future<Void>> consumeFutures = this.consume();
        for(Future future: wordCountMapList){
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        this.populateWordQueueFinished.set(true);//finish to load word count map to word queue

        for(Future<Void> future: consumeFutures){
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Finish");

    }

}

