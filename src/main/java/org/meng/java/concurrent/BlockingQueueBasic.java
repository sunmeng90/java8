package org.meng.java.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by meng_ on 7/1/2017.
 */
public class BlockingQueueBasic {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

        Producer producer1 = new Producer(queue);
        Producer producer2 = new Producer(queue);
        Producer producer3 = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer1).start();
        new Thread(producer2).start();
        new Thread(producer3).start();

        new Thread(consumer).start();
    }


}

class Producer implements Runnable {
    private BlockingQueue<String> queue;

    private static AtomicInteger count = new AtomicInteger(0);

    public Producer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            try {
                String item = Thread.currentThread().getName() + " => " + (count.incrementAndGet());
                queue.put(item);
                //System.out.println("Put "+ item);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}


class Consumer implements Runnable {
    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            try {
                String item = queue.poll();
//                System.out.println("Pop "+" => "+item);
                System.out.println(queue);
                Thread.sleep(200);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}