package com.lq.helloworld.okhttp;

public class Request {
    private String param;


    public Request(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
