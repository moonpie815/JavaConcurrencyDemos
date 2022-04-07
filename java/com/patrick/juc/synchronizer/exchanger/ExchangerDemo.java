package com.patrick.juc.synchronizer.exchanger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Exchanger;

import static java.util.concurrent.CompletableFuture.runAsync;

public class ExchangerDemo {

    //Also, note that the first thread's call may timeout if the second thread does not reach the exchange point in time.
    // How long the first thread should wait can be controlled using the overloaded exchange(T t, long timeout, TimeUnit timeUnit).
    public static void main(String[] args) throws InterruptedException {
        ExchangerDemo exchangerDemo = new ExchangerDemo();
        exchangerDemo.givenThreads_whenMessageExchanged_thenCorrect();
//        exchangerDemo.givenThread_WhenExchangedMessage_thenCorrect();
    }

    public void givenThreads_whenMessageExchanged_thenCorrect() {
        Exchanger<String> exchanger = new Exchanger<>();

        Runnable taskA = () -> {
            try {
                String message = exchanger.exchange("from A");
//                assertEquals("from B", message);
                System.out.println("message from B: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };

        Runnable taskB = () -> {
            try {
                String message = exchanger.exchange("from B");
//                assertEquals("from A", message);
                System.out.println("message from A: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };
        CompletableFuture.allOf(
                runAsync(taskA), runAsync(taskB)).join();
    }

    public void givenThread_WhenExchangedMessage_thenCorrect() throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();

        Runnable runner = () -> {
            try {
                String message = exchanger.exchange("from runner");
//                assertEquals("to runner", message);
                System.out.println("1 message to runner: " + message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        };
        CompletableFuture<Void> result
                = runAsync(runner);
        String msg = exchanger.exchange("to runner");
        System.out.println("2 message to runner: " + msg);
//        assertEquals("from runner", msg);
        result.join();
    }
}
