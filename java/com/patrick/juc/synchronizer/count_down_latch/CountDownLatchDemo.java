package com.patrick.juc.synchronizer.count_down_latch;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(6);
        LatchDemo latchDemo = new LatchDemo(countDownLatch);
        Long start = System.currentTimeMillis();
        for (int i = 0; i < countDownLatch.getCount(); i++) {
            //why use same object
            new Thread(latchDemo).start();
        }
        try {
            countDownLatch.await();
            System.out.println("CountDownLatch is zero.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Long end = System.currentTimeMillis();
        System.out.println("Start time: " + start);
        System.out.println("End time  : " + end);
        System.out.println("Tocal execution time is: " + (end - start));
    }

}

class LatchDemo implements Runnable {

    private CountDownLatch countDownLatch;

    public LatchDemo(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i < 5; i++) {
                    if (i % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + ", i = " + i);
                        Thread.sleep(200);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName() + ", countDownLatch.getCount(): " + countDownLatch.getCount());
            }
        }
    }
}