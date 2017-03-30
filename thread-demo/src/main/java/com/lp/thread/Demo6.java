package com.lp.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Product1 {
    private String name;
    private int count;
    private boolean flag;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();
    
    private Condition condition1 = lock.newCondition();

    public void produce(String name) {
        lock.lock();
        try {
            while (this.flag) {
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.name = name;
            System.out.println(Thread.currentThread().getName() + "生产了" + this.count + "个" + this.name);
            this.count++;
            this.flag = true;
            condition1.signal();

        } finally {
            //lock.unlock();
        }
    }

    public void consume() {
        lock.lock();
        try {
            while (!this.flag) {
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "消费了" + this.count + "个" + this.name);
            this.flag = false;
            condition.signal();
        } finally {
            //lock.unlock();
        }
    }
}

class Produce1 implements Runnable {
    private Product1 product;

    public Produce1(Product1 product) {
        super();
        this.product = product;
    }

    public void run() {
        while (true) {
            this.product.produce("MacBook Pro");
        }
    }

}

class Consume1 implements Runnable {
    private Product1 product;

    public Consume1(Product1 product) {
        super();
        this.product = product;
    }

    public void run() {
        while (true) {
            this.product.consume();
        }
    }

}

public class Demo6 {

    public static void main(String[] args) throws InterruptedException {
        Product1 product = new Product1();
        Produce1 produce = new Produce1(product);
        Consume1 consume = new Consume1(product);
        new Thread(produce).start();
        new Thread(consume).start();
        new Thread(produce).start();
        new Thread(consume).start();
        Thread.sleep(50);
        System.exit(1);
    }

}
