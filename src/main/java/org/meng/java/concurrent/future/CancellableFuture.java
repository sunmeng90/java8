package org.meng.java.concurrent.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Demonstration of how to cancel a (future) task
 *
 */
@Slf4j
public class CancellableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        noCancel();
//        cancelTaskAndMayInterrupt();
        cancelFutureButTaskNotCancelled();
    }

    /**
     * No cancel
     *
     * @throws InterruptedException
     * @throws ExecutionException
     */
    private static void noCancel() throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            Thread.sleep(10 * 5000);
            return "hello world";
        });

        new Thread(futureTask).start();
        log.info("Task cancelled: " + futureTask.isCancelled());
        log.info("Task done: " + futureTask.isDone());
        log.info("Task result:" + futureTask.get());
        log.info("Task done: " + futureTask.isDone());
    }

    /**
     * Cancel task when task is running. The task itself will respond to a cancel flag(interrupt) and stop running
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void cancelTaskAndMayInterrupt() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            try {
                Thread.sleep(10 * 1000);//when detect interrupt flag will (respond) throw exception
            } catch (InterruptedException e) {
                log.error("future task interrupted", e);
            }
            return "hello world";
        });

        new Thread(futureTask).start();
        log.info("Task cancelled: " + futureTask.isCancelled());
        log.info("Task done: " + futureTask.isDone());
        //mayInterruptIfRunning: set the interrupt status of worker thread
        futureTask.cancel(true);//when true, the task thread will interrupt flag will be set
        log.info("Task cancelled: " + futureTask.isCancelled());
        log.info("Task done: " + futureTask.isDone());
        log.info("Task result:" + futureTask.get());
        log.info("Task cancelled: " + futureTask.isCancelled());
        log.info("Task done: " + futureTask.isDone());
    }

    /**
     * Future is cancelled, but task will not be cancelled even it is interrupted, because task doesn't respond to
     * interrupt when wait for an intrinsic lock
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void cancelFutureButTaskNotCancelled() throws ExecutionException, InterruptedException {
        Object lock = new Object();//simulate a contention on intrinsic lock
        new Thread(() -> {//the first thread will get the lock
            synchronized (lock) {
                try {
                    log.info(Thread.currentThread().getName() + " gets lock");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(1000);
        //the future task will be blocking and wait for intrinsic lock
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            log.info("Future task is waiting for lock");
            synchronized (lock) {
                log.info("Future get lock");
                return "hello world";
            }
        });

        new Thread(futureTask).start();
        log.info("Task cancelled: " + futureTask.isCancelled());
        log.info("Task done: " + futureTask.isDone());
        //mayInterruptIfRunning: set the interrupt status of worker thread
        //the worker will not be cancelled because worker is running blocking logic that can't respond to interrupt
        futureTask.cancel(true);
        //true, but the thread will continue running, until it gets the lock and then respond to the interrupt signal
        log.info("Task cancelled: " + futureTask.isCancelled());
        log.info("Task done: " + futureTask.isDone());
        log.info("Task result:" + futureTask.get());
        log.info("Task cancelled: " + futureTask.isCancelled());
        log.info("Task done: " + futureTask.isDone());
    }
}
