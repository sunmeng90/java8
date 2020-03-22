package org.meng.java.thread.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

public class ThreadPoolDemo {

    public static void main(String[] args) {
        ThreadPool pool = new DefaultThreadPool();
        List<FutureTask> jobs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            FutureTask task = new FutureTask(() -> {
                System.out.println(Thread.currentThread().getName() + " start.");
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " end.");
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted");
                }
                return null;
            });
            jobs.add(task);
            pool.execute(task);
        }

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                System.out.println("will cancel task " + i);
                // job may finished earlier than cancel operation, so cancel doesn't always succeed
                jobs.get(i).cancel(true);
            }
        }

        for (int i = 0; i < 20; i++) {
            try {
                System.out.println("get result for task " + i);
                jobs.get(i).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pool.shutdown();
    }
}
