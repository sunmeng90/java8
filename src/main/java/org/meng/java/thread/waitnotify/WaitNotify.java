package org.meng.java.thread.waitnotify;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class WaitNotify {
    static boolean flag = true;  // need to wait
    static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);
        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    private static String getCurrentDateTime() {
        return DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());
    }

    static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    try {
                        System.out.println(String.format(Thread.currentThread() + " flag is true. Wait @ " + getCurrentDateTime()));
                        lock.wait();
                    } catch (InterruptedException e) {

                    }
                }
                System.out.println(Thread.currentThread() + " flag is false. running @ " + getCurrentDateTime());
            }
        }
    }


    static class Notify implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(String.format(Thread.currentThread() + " hold lock. notify @ " + getCurrentDateTime()));
                lock.notifyAll();
                flag = false;
                SleepUtils.second(5);
            }
            synchronized (lock) {
                System.out.println(String.format(Thread.currentThread() + " hold lock again. sleep @ " + getCurrentDateTime()));
                SleepUtils.second(5);
            }
        }
    }

    static class SleepUtils {
        public static final void second(long seconds) {
            try {
                TimeUnit.SECONDS.sleep(seconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
