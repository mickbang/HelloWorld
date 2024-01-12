package com.client.javalib;

public class ThreadWaitNotifyJoin {

    static class WaitDemo {
        private String name;

        public synchronized void init() {
            name = "Hello world!";
            //notify();
            notifyAll();
        }


        public synchronized void printName() {
            while (name == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(name);
        }

    }

    public static void main(String[] args) {
        final WaitDemo demo = new WaitDemo();


        final Thread waitThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                demo.init();
            }
        });

        Thread waitThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //waitThread1.join()也能与上面的wait()达到相同的效果
//                Thread.yield();短暂让出时间段给同优先级的线程
                demo.printName();
            }
        });

        waitThread1.start();
        waitThread2.start();
    }
}
