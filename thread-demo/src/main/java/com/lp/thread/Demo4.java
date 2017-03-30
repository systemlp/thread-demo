package com.lp.thread;

class Student1 {
    String name;
    String sex;

    boolean flag;
}

class Input implements Runnable {
    private Student1 stu;

    public Input(Student1 stu) {
        this.stu = stu;
    }

    public void run() {
        boolean flag = true;
        while (true) {
            synchronized (stu) {
                if (stu.flag) {
                    try {
                        stu.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (flag) {
                    stu.name = "Tom";
                    stu.sex = "男";
                } else {
                    stu.name = "Alice";
                    stu.sex = "女";
                }
                stu.flag = true;
                stu.notify();
            }
            flag = !flag;
        }
    }
}

class Ouput implements Runnable {

    private Student1 stu;

    public Ouput(Student1 stu) {
        this.stu = stu;
    }

    public void run() {
        while (true) {
            synchronized (stu) {
                if (!stu.flag) {
                    try {
                        stu.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(stu.name + ":" + stu.sex);
                stu.flag = false;
                stu.notify();
            }
        }
    }

}

public class Demo4 {

    public static void main(String[] args) {
        Student1 stu = new Student1();
        new Thread(new Input(stu)).start();
        new Thread(new Ouput(stu)).start();
    }

}
