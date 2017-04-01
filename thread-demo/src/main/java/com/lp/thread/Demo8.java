package com.lp.thread;

import java.util.ArrayList;
import java.util.List;

class UploadUser {

    public static int total;
    public static int currIndex;

    public String upload() {
        // 模拟文件读取过程
        final List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < 50; i++) {
            list.add(new Object());
        }
        total = list.size();
        new Thread(new Runnable() {

            public void run() {
                try {
                    for (int i = 1; i <= list.size(); i++) {
                        Thread.sleep(300);
                        System.out.println("上传至第" + i + "个对象:" + list.get(i - 1));
                        currIndex = i;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return "文件开始上传。";
    }

}

public class Demo8 {

    public static void main(String[] args) throws InterruptedException {
        final UploadUser uploadUser = new UploadUser();
        System.out.println(uploadUser.upload());
        new Thread(new Runnable() {

            public void run() {
                while (UploadUser.currIndex <= UploadUser.total) {
                    if (UploadUser.currIndex == UploadUser.total) {
                        System.out.println("上传完成");
                        break;
                    }
                    System.out.println("当前上传进至第" + UploadUser.currIndex + "条对象");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

}
