package org.meng.java.thread.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    private static final int MAX_WORKER_NUMBER = 10;

    private static final int DEFAULT_WORKER_NUMBER = 5;

    private static final int MIN_WORKER_NUMBER = 1;

    private final LinkedList<Job> jobs = new LinkedList<>();

    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    private int workerNum = DEFAULT_WORKER_NUMBER;

    private final AtomicLong threadSeq = new AtomicLong();

    public DefaultThreadPool() {
        initWorkers(DEFAULT_WORKER_NUMBER);
    }

    public DefaultThreadPool(int num) {
        if (num > MAX_WORKER_NUMBER) {
            this.workerNum = MAX_WORKER_NUMBER;
        } else if (num < MIN_WORKER_NUMBER) {
            this.workerNum = MIN_WORKER_NUMBER;
        } else {
            this.workerNum = num;
        }
        initWorkers(num);
    }

    void initWorkers(int workerNum) {
        for (int i = 0; i < workerNum; i++) {
            Worker worker = new Worker();
            this.workers.add(worker);
            Thread workerThread = new Thread(worker, "DefaultThreadPool-Worker-" + threadSeq.incrementAndGet());
            workerThread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker : this.workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            if (num + this.workerNum > MAX_WORKER_NUMBER) {
                num = this.workerNum + num - MAX_WORKER_NUMBER;
            }
            initWorkers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorkers(int num) {
        synchronized (jobs) {
            if (num >= this.workerNum) {
                throw new IllegalArgumentException("beyond worker number");
            }
            for (int i = 0; i < num; i++) {
                Worker worker = this.workers.remove(0);
                worker.shutdown();
            }
            this.workerNum -= num;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    class Worker implements Runnable {

        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            // release the lock and wait, if jobs queue are empty, worker thread will never exit by flag `running`
                            jobs.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutdown() {
            this.running = false;
        }
    }
}
