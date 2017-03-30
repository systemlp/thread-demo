package com.lp.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Goods {
    private String name;
    private double price;

    public Goods() {
        super();
    }

    public Goods(String name, double price) {
        super();
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Goods [商品=" + name + ", 价格=" + price + "]";
    }

    private Goods[] goodsArray = new Goods[100];
    private int prodIndex;
    private int consIndex;
    private int count;

    private Lock lock = new ReentrantLock();
    // 控制生产者等待与唤醒
    private Condition prodCon = lock.newCondition();
    // 控制消费者等待与唤醒
    private Condition consCon = lock.newCondition();

    public void produce() {
        lock.lock();
        try {
            while (count == goodsArray.length) {
                try {
                    prodCon.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            goodsArray[prodIndex] = new Goods("iPhone 8", 7688);
            System.out.println(Thread.currentThread().getName() + "生产了" + (++this.count) + "台"
                    + goodsArray[prodIndex].toString());
            if (++prodIndex == goodsArray.length) {
                prodIndex = 0;
            }
            consCon.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consume() {
        lock.lock();
        try {
            while (count == 0) {
                try {
                    consCon.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "购买了的第" + (this.count--) + "台"
                    + goodsArray[consIndex].toString());
            if (++consIndex == goodsArray.length) {
                consIndex = 0;
            }
            prodCon.signal();
        } finally {
            lock.unlock();
        }
    }
}

class Product2 implements Runnable {
    private Goods goods;

    public Product2(Goods goods) {
        super();
        this.goods = goods;
    }

    public void run() {
        while (true) {
            goods.produce();
        }
    }
}

class Consume2 implements Runnable {
    private Goods goods;

    public Consume2(Goods goods) {
        super();
        this.goods = goods;
    }

    public void run() {
        while (true) {
            goods.consume();
        }
    }
}

public class Demo7 {

    public static void main(String[] args) throws InterruptedException {
        Goods goods = new Goods();
        Product2 product2 = new Product2(goods);
        Consume2 consume2 = new Consume2(goods);
        
        new Thread(product2).start();
        new Thread(consume2).start();
        new Thread(product2).start();
        new Thread(consume2).start();
        
        Thread.sleep(50);
        System.exit(1);
    }
}
