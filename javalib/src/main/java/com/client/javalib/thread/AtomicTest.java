package com.client.javalib.thread;

import java.util.concurrent.atomic.AtomicInteger;



public class AtomicTest implements Runnable{
    private int s = 1;
    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        s = 10;
    }
    public static void main(String[] args) {
//        ThreadDemo td = new ThreadDemo();
//        new Thread(td).start();
//        while(true){
//            if(td.isFlag()){
//                System.out.println("------------------");
//                break;
//            }
//        }

        ThreadDemo1 test = new ThreadDemo1();
        new Thread(test).start();
        while (true){
            //private volatile int s = 0;  System.out.println(test.getS()); 在不使用volatile关键字时，s的值可能不会被立即更新到主内存中，导致线程2读取到的值可能不是最新的。
            // （System.out.println内部使用了synchronized修饰会导致当前线程同步s）
            // 1.volatile 可见性     Atomicxxx修改原子性和可见性   两个线程能获取到最新值
            //
            if (test.getS() == 10) {
                System.out.println("==================修正了结果");
                break;
            }
        }


    }

    static class ThreadDemo1 implements Runnable{
        private AtomicInteger s = new AtomicInteger(0);
//        private volatile int s = 0;
        @Override
        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            s.set(10);
            System.out.println("flag=" + s);
        }

        public int getS(){
            return s.intValue();
        }
    }




    static class ThreadDemo implements Runnable {
        private volatile   boolean flag = false;
        @Override
        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            flag = true;
            System.out.println("flag=" + isFlag());
        }

        public boolean isFlag() {
            return flag;
        }
    }
}
