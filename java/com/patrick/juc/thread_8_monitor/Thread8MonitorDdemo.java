package com.patrick.juc.thread_8_monitor;

public class Thread8MonitorDdemo {
    public static void main(String[] args) {
        System.out.println("main method.");

        Number number1 = new Number();
        Number number2 = new Number();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number1.getOne();
//                number2.getOne();
//                if getOne() is static synchronized, same number1 with getOne() and getTwo(), returns: One, Two
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number1.getTwo();
//                number2.getTwo();
//                if this line is number1.getTwo(); it would be sleep since in getOne, thread is sleeping for 5 seconds.
//                because thread with getOne() got init first,
//                if this line is number2.getTwo(); it would not sleep.
            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                number1.getThree();
//            }
//        }).start();
    }
}

class  Number {
//    public static synchronized void getOne() {
    public synchronized void getOne() {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("One---------------------------");
    }

    public synchronized void getTwo() {
        System.out.println("Two************************");
    }

    public void getThree() {
        System.out.println("Three========================");
    }
}
