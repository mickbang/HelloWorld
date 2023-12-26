package com.lq.helloworld.okhttp;

import java.io.IOException;


public class RealCall {
    private OkhttpClient okhttpClient;
    private Request request;

    public RealCall(OkhttpClient okhttpClient, Request request) {
        this.okhttpClient = okhttpClient;
        this.request = request;
    }

    public Response execute() throws IOException {
        okhttpClient.getDispatcher().execute(this);

        return new Response(request.getParam());
    }

    public void enqueue(Callback responseCallback) {
        okhttpClient.getDispatcher().enqueue(new AsyncCall(responseCallback));
    }


    class AsyncCall implements Runnable{
        private Callback responseCallback;
        public AsyncCall(Callback responseCallback) {
            this.responseCallback = responseCallback;
        }

        public RealCall get(){
            return RealCall.this;
        }
        @Override
        public void run() {

        }
    }
}
