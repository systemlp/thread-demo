package com.lp.thread;

class Product {
    private String name;
    private int count;
    private boolean flag;

    public synchronized void produce(String name) {
        while (this.flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.name = name;
        System.out.println(Thread.currentThread().getName() + "生产了" + this.count + "个" + this.name);
        this.count++;
        this.flag = true;
        notifyAll();
    }

    public synchronized void consume() {
        while (!this.flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "消费了" + this.count + "个" + this.name);
        this.flag = false;
        notifyAll();
    }

}

class Produce implements Runnable {
    private Product product;

    public Produce(Product product) {
        super();
        this.product = product;
    }

    public void run() {
        while (true) {
            this.product.produce("MacBook Pro");
        }
    }

}

class Consume implements Runnable {
    private Product product;

    public Consume(Product product) {
        super();
        this.product = product;
    }

    public void run() {
        while (true) {
            this.product.consume();
        }
    }

}

public class Demo5 {

    public static void main(String[] args) throws InterruptedException {
        Product product = new Product();
        Produce produce = new Produce(product);
        Consume consume = new Consume(product);
        new Thread(produce).start();
        new Thread(consume).start();
        new Thread(produce).start();
        new Thread(consume).start();
        Thread.sleep(50);
        System.exit(1);
    }

}
