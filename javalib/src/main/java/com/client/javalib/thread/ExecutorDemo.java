package com.client.javalib.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//android 多线程 AsyncTask Executor IntentService
public class ExecutorDemo {

    public static void main(String[] args) {
//        executorDemo();
        executorCall();
    }

    static void executorDemo() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello World");
            }
        });
    }

    static void executorCall() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2_000);
                return "Hello ZhangSan";
            }
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(callable);
//        if (future.isDone()) {
//
//        }
        try {
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
