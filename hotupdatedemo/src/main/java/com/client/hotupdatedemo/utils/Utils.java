package com.client.hotupdatedemo.utils;

public class Utils {
    private String name;

    private Utils(String name) {
        this.name = name;
    }

    private void shout() {
        System.out.println("我执行过了吗，我是插件代码" + " : " + name);
    }
}
