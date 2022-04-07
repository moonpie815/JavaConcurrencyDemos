package com.patrick.juc.synchronizer.phaser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class PhaserDemo {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        //Creates a new phaser with the given number of registered unarrived parties, no parent, and initial phase number 0.
        Phaser ph = new Phaser(1);
        System.out.println("0 Phase******  " + ph.getPhase() + ", ph.getRegisteredParties()  " + ph.getRegisteredParties());

        //Next, let's start three LongRunningAction action threads, which will be waiting on the barrier until we will
        // call the arriveAndAwaitAdvance() method from the main thread.
        executorService.submit(new LongRunningAction("thread-1", ph));
        executorService.submit(new LongRunningAction("thread-2", ph));
        executorService.submit(new LongRunningAction("thread-3", ph));
        ph.arriveAndAwaitAdvance();//then threads started above started to execute
        System.out.println(" phase completed.\n");
        System.out.println("1 Phase******  " + ph.getPhase() + ", ph.getRegisteredParties(): " + ph.getRegisteredParties());
//        ph.arriveAndDeregister(); //add this line, get phase would be negative(sometimes). why?


//        executorService.submit(new LongRunningAction("thread-4", ph));
//        executorService.submit(new LongRunningAction("thread-5", ph));
//        executorService.submit(new LongRunningAction("thread-6", ph));
//        executorService.submit(new LongRunningAction("thread-7", ph));
//        executorService.submit(new LongRunningAction("thread-8", ph));
////        ph.arriveAndAwaitAdvance(); seems no need
//        System.out.println(" phase completed..\n");
//        System.out.println("2 Phase******  " + ph.getPhase() + ", ph.getRegisteredParties(): " + ph.getRegisteredParties());
//        ph.arriveAndDeregister();
//        System.out.println("arriveAndDeregister ph.getRegisteredParties(): " + ph.getRegisteredParties());

        executorService.shutdown();
        System.out.println(executorService.isShutdown());
        System.out.println(executorService.isTerminated());

    }

}


class LongRunningAction implements Runnable {
    private String threadName;
    private Phaser ph;

    LongRunningAction(String threadName, Phaser ph) {
        this.threadName = threadName;
        this.ph = ph;
        ph.register();
    }

    @Override
    public void run() {
        System.out.println("    This is phase " + ph.getPhase() + ", Thread: " + threadName + " in run() method, before long running action. - Thread "
                + threadName + "ph.getRegisteredParties(): " + ph.getRegisteredParties());
//        try {
//            Thread.sleep(100);
//            System.out.println("    Thread: " + threadName + ".....");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        ph.arriveAndAwaitAdvance();
        System.out.println("    Thread: " + threadName + " after....");
        ph.arriveAndDeregister();
    }
}