package com.lp.thread;

//创建该类用于产生垃圾
class Garbage{
    
    /*
     * 考虑到所有的对象都可以被当做垃圾回收 
     * 因此垃圾回收机制的代码应该在所有类的父类的当中 
     * 即Object类中的finalize()方法
     * 重写该方法便于验证是否执行
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("垃圾回收线程抢占CPU成功！");
    }
}
/**
 * 
 * 〈通过利用垃圾回收机制证明JVM虚拟机是多线程程序〉<br> 
 * 〈功能详细描述〉
 *
 * @author admin
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class Demo {

    public static void main(String[] args) {
        /*
         * main()方法是程序的入口 
         * 因此以下代码都属于主线程部分
         */

        /*
         * 创建多个匿名对象 
         * 匿名对象在创建后就直接成为了垃圾 
         * 因此可触发垃圾回收机制
         */
        new Garbage();
        new Garbage();
        new Garbage();
        new Garbage();
        new Garbage();

        /*
         * 因为垃圾量少且垃圾回收机制的优先级本身就低 
         * 因此使用System类中的强制垃圾回收方法gc() 
         * 这样主线程运行至此就会启动垃圾回收线程
         * 执行finalize()方法
         */
        System.gc();// 垃圾回收线程启动，此时同时存在2个线程
        System.out.println("主线程抢占CPU成功！");
    }

}
