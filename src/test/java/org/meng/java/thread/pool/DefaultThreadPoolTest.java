package org.meng.java.thread.pool;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

class DefaultThreadPoolTest {

    @Test
    public void test() throws InterruptedException {
        ThreadPool pool = new DefaultThreadPool();
        List<FutureTask> jobs = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            final int tempInt = i;
            FutureTask task = new FutureTask(() -> {
                System.out.println(Thread.currentThread().getName() + " start task " + tempInt);
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + " end task " + tempInt);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted task " + tempInt);
                }
                return null;
            });
            jobs.add(task);
            pool.execute(task);
        }

        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                System.out.println("will cancel task " + i);
                jobs.get(i).cancel(true);
            }
        }

        for (int i = 0; i < 20; i++) {
            try {
                jobs.get(i).get();
                System.out.println("get result for task " + i);
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        pool.shutdown();
    }

    //TODO: sometimes, there are only 9 task end, it's wired, (the pool(workers) doesn't closed)

// DefaultThreadPool-Worker-1 start.
// DefaultThreadPool-Worker-2 start.
// DefaultThreadPool-Worker-4 start.
// DefaultThreadPool-Worker-3 start.
// DefaultThreadPool-Worker-5 start.
// DefaultThreadPool-Worker-4 interrupted
// DefaultThreadPool-Worker-4 start.
// DefaultThreadPool-Worker-2 interrupted
// DefaultThreadPool-Worker-1 interrupted
// DefaultThreadPool-Worker-1 start.
// DefaultThreadPool-Worker-2 start.
// DefaultThreadPool-Worker-4 end.
// DefaultThreadPool-Worker-4 start.
// DefaultThreadPool-Worker-5 end.
// DefaultThreadPool-Worker-5 start.
// DefaultThreadPool-Worker-1 end.
// DefaultThreadPool-Worker-1 start.
// DefaultThreadPool-Worker-3 end.
// DefaultThreadPool-Worker-3 start.
// DefaultThreadPool-Worker-2 end.
// DefaultThreadPool-Worker-3 end.
// DefaultThreadPool-Worker-5 end.
// DefaultThreadPool-Worker-1 end.
// DefaultThreadPool-Worker-4 end.

}