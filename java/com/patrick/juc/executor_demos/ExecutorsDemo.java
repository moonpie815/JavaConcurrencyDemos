package com.patrick.juc.executor_demos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorsDemo {
    private static ExecutorService executor = Executors.newFixedThreadPool(15);

    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            executor.execute(new SubThread());
        }
    }
}

class SubThread implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //do nothing
        }
    }
}