package com.lp.thread;

import java.util.concurrent.CountDownLatch;

class Num implements Runnable {
    private int count;

    public Num() {
        count = 0;
    }

    public synchronized void run() {//获取锁并排斥其他线程
        /*
         * 使用synchronized修饰run()方法 
         * 被修饰的方法称为同步方法 
         * 其作用的范围是整个方法 
         * 作用的对象是调用这个方法的对象
         */
        for (int i = 0; i < 5; i++) {

            System.out.println(Thread.currentThread().getName() + "数了" + (++count));
        }
    }//释放锁

}

class Num2 implements Runnable {
    private static int count;

    public Num2() {
        count = 0;
    }
    
    private synchronized static void fun(){//获取锁并排斥其他线程
        for (int i = 0; i < 5; i++) {

            System.out.println(Thread.currentThread().getName() + "数了" + (++count));
        }
    }

    public void run() {
        fun();
    }//释放锁

}

class Num3 implements Runnable {
    private int count;

    public Num3() {
        count = 0;
    }

    public void run() {//获取锁并排斥其他线程
        /*
         * 使用synchronized修饰run()方法 
         * 被修饰的方法称为同步方法 
         * 其作用的范围是整个方法 
         * 作用的对象是调用这个方法的对象
         */
        synchronized (this) {
            for (int i = 0; i < 5; i++) {
                
                System.out.println(Thread.currentThread().getName() + "数了" + (++count));
            }
        }//释放锁
    }

}

public class Demo3 {

    public static void main(String[] args) throws InterruptedException {
        Num n = new Num();
        /*
         * 使用Thread(Runnable target, String name) 这种构造方法
         * 参数name就是新线程名称
         */
        Thread thread1 = new Thread(n, "Tom");
        Thread thread2 = new Thread(n, "Mike");
        thread1.start();
        thread2.start();
        
        Thread thread3 = new Thread(new Num2(), "Tony");
        Thread thread4 = new Thread(new Num2(), "Jim");
        thread3.start();
        thread4.start();
        
        Num3 num3=new Num3();
        Thread thread5 = new Thread(num3, "jetty");
        Thread thread6 = new Thread(num3, "jack");
        thread5.start();
        thread6.start();
    }
}
