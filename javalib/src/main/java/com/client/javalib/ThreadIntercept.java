package com.client.javalib;

//Thread 的停止
//1 使用Thread.stop() 这种方式会导致线程停止后，状态不正常，容易产生数据不一致的情况。不建议使用，你也不知道他会在什么时候停止
//2 使用volatile boolean flag = true; 配合线程的run方法中的while循环，当flag为false时，停止线程
//3 使用Thread.interrupt() 配合线程的run方法中的while循环，当线程被中断时，停止线程(判断是否interrupt，然后手动停止)
//(注意Thread.interrupt()与thread.interrupt()区别)  ==》Thread.interrupt()会清除当前线程的中断标志位，所以需要再次判断
//线程如果在sleep,wait,join中，会抛出InterruptedException异常，从而停止线程，所以需要捕获该异常

//4 使用ReentrantLock配合Condition的await()方法，当线程被中断时，停止线程
//
public class ThreadIntercept {
    static class  MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 1_000_000; i++) {
                //Thread.interrupted()  这个判断会清除当前线程的中断标志位，所以需要再次判断
                if (isInterrupted()){
                    System.out.println("stop!");
                    break;
                }
                System.out.println(i);


//                try {
//                    Thread.sleep(1_000);
//                } catch (InterruptedException e) {
//
//                }
            }
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        myThread.stop();
        myThread.interrupt();
    }

}
