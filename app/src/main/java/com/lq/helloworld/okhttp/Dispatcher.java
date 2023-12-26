package com.lq.helloworld.okhttp;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Dispatcher {

    private Deque<RealCall.AsyncCall> runningAsyncCall = new ArrayDeque<>();
    private Deque<RealCall.AsyncCall> readyAsyncCall = new ArrayDeque<>();
    private Deque<RealCall> runningSyncCall = new ArrayDeque<>();

    private ExecutorService executor = Executors.newCachedThreadPool();

    public void execute(RealCall call) {
        runningSyncCall.add(call);
    }

    public void enqueue(RealCall.AsyncCall call) {
        runningAsyncCall.add(call);
        executor.execute(call);
    }
}
