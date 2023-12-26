package com.lq.helloworld.okhttp;

public class OkhttpClient {
    private Dispatcher dispatcher;
    public  RealCall newCall(Request request){
        return new RealCall(this,request);
    }


    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
