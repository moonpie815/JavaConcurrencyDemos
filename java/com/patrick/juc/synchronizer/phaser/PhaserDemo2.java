package com.patrick.juc.synchronizer.phaser;

import java.util.concurrent.Phaser;

public class PhaserDemo2 {
    public static void main(String[] args) {
        Phaser ph = new Phaser(1);
        // registering 3 parties in bulk
        ph.bulkRegister(3);
        System.out.println("Phase in Main " + ph.getPhase() + " started");
        // starting 3 threads
        for(int i = 0; i < 3; i++) {
            new Thread(new FirstTask("Thread-"+i, ph)).start();
        }
        // This is to make main thread wait
        ph.arriveAndAwaitAdvance();
        System.out.println("Phase in Main-1 " +  ph.getPhase() + " completed");
//        ph.arriveAndDeregister(); // add this getPhase return nagetive, seems phaser is shutdown(sometimes)
        System.out.println("Phase in Main-1, getRegisteredParties: " +  ph.getRegisteredParties() + "\n");

        for(int i = 0; i < 5; i++) {
            new Thread(new SecondTask("Thread-"+i, ph)).start();
        }
        ph.arriveAndAwaitAdvance(); // is this needed
        System.out.println("Phase in Main-2 " + ph.getPhase() + " completed\n");
        System.out.println("Phase in Main-2, getRegisteredParties: " +  ph.getRegisteredParties() + "\n");
        // deregistering the main thread
        ph.arriveAndDeregister();
    }
}

class FirstTask implements Runnable {
    private String threadName;
    private Phaser ph;

    FirstTask(String threadName, Phaser ph){
        this.threadName = threadName;
        this.ph = ph;
    }
    @Override
    public void run() {
        System.out.println("In First Task.. " + threadName);
        // parties will wait here
        ph.arriveAndAwaitAdvance();

        System.out.println("Deregistering, Phase******  "+ ph.getPhase() + " Completed");
        ph.arriveAndDeregister();
    }
}

class SecondTask implements Runnable {
    private String threadName;
    private Phaser ph;

    SecondTask(String threadName, Phaser ph){
        this.threadName = threadName;
        this.ph = ph;
        ph.register();
    }

    @Override
    public void run() {
        System.out.println("In SecondTask.. " + threadName);
        ph.arriveAndAwaitAdvance();
        System.out.println("In SecondTask.. Phase****** " + ph.getPhase() + " completed" + threadName);
        ph.arriveAndDeregister();
    }
}
