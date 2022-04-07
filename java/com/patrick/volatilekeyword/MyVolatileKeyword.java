package com.patrick.volatilekeyword;

/*
 * If flag is not declared as volatile, then 'System.out.println("out put from main method()");' would not be executed,
 * because there is a Thread Visibility issue(线程可见性问题)
 *
 * */


public class MyVolatileKeyword {
    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        Thread thread = new Thread(td);
        thread.start();
//        thread.run();
        //thread.run() could get --- out put, why? because start() would start a new thread, call run() is just like calling another method.
        //https://stackoverflow.com/questions/8579657/whats-the-difference-between-thread-start-and-runnable-run
//        New Thread creation: When a program calls the start() method, a new thread is created and then the run() method
//        is executed. But if we directly call the run() method then no new thread will be created and run() method will
//        be executed as a normal method call on the current calling thread itself and no multi-threading will take place.

        while (true) {
            if (td.isFlag()) {
                System.out.println("Output from main method()");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable {
    private boolean flag = false;
//    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("Thread is awake now. Output from from Thread.");
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}