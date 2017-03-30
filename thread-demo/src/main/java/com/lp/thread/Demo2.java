package com.lp.thread;

//实现两位学生同时回答问题的效果

class Student extends Thread {
    // 创建Student类继承Thread类
    private String name;

    public Student() {
        super();
    }

    public Student(String name) {
        super();
        this.name = name;
    }

    public void run() {
        /*
         * 之前讲过 每个线程需要执行的代码被称为任务代码，
         * 且都有其存储位置 此处重写run()方法 
         * 就是创建任务代码 因此run()方法也就是任务代码的存储位置
         */
        for (int i = 1; i <= 10; i++) {
            System.out.println(name + "回答了第" + i + "个问题");
            /*
             * currentThread()是一个静态方法 返回值是当前正在执行的线程的对象 再调用对象的getName()方法 显示当前线程的名称
             */
            // System.out.println(Thread.currentThread().getName()+"回答了第"+i+"个问题");
        }
    }

}

public class Demo2 {

    public static void main(String[] args) {
        /*
         * 由于Student类继承了Thread类 每次创建Student子类对象时 就相当于新创建了一个线程
         */
        Student s1 = new Student("Tom");
        /*
         * 主线程运行至创建s2对象 此时整个程序只有2个线程 主线程以及垃圾回收线程 两个子线程目前仅仅是创建 尚未启动，因此不会抢占cpu
         */
        Student s2 = new Student("Mike");
        /*
         * run()方法只是普通的方法调用 而start()方法可以启动线程 主线程的任务代码存储在main()方法中 子线程的任务代码存储在run()方法中
         */
        s1.start();// 主线程执行至此，同时存在3个线程
        s2.start();// 主线程执行至此，同时存在4个线程

//        System.out.println("提问！");
        // 显示主线程名称
         System.out.println(Thread.currentThread().getName()+"提问！");

    }

}
