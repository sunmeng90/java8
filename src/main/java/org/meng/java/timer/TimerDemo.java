package org.meng.java.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerDemo {
    public static void main(String[] args) throws InterruptedException {
        //run a task once
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Run task 3 seconds after application startup");
            }
        }, 3 * 1000);

        //run a task at the specific time
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Run a task at the specific time");
            }
        }, new Date());

        //Run a task repeatedly at a fixed delay after a fixed duration
        //each execution is scheduled relative to the actual execution time of the previous execution. If an execution is delayed for any reason (such as garbage collection or other background activity), subsequent executions will be delayed as well
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("Run a task every 1 seconds after 5 seconds delay");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5*1000, 1000);

        //Run a task repeatedly at a fixed delay after a fixed duration
        //each execution is scheduled relative to the scheduled execution time of the initial execution. If an execution is delayed for any reason (such as garbage collection or other background activity), two or more executions will occur in rapid succession to "catch up."
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("Run a task every 1 seconds after 10 seconds delay at fixed rate");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },10*1000, 1);

        //timeout a task after a specified duration
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        class WorkerThread implements Runnable{
            private String command;

            public WorkerThread(String command) {
                this.command = command;
            }

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" start at"+new Date());
                processCommand();
                System.out.println(Thread.currentThread().getName()+" finish at"+new Date());
            }

            private void processCommand() {
                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        for(int i=0; i<10;i++){
            Thread.sleep(1000);
            executorService.scheduleAtFixedRate(new WorkerThread("Heavy Task "+i), 2, 5, TimeUnit.SECONDS);
        }

        Thread.sleep(3000);
        executorService.shutdown();
        while (!executorService.isTerminated()){
            //nothing waiting for all task to finish
        }
        System.out.println("All task processed.");

    }
}
