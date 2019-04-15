package com.zookeeper.zookeeperdistribut.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: LinSong
 * @Date: 2019/4/15 16:46
 */
public class ConcurrentTestDemo {
    public static void main(String[] args) {
        //并发数
        int current = 20;

        //循环屏障
        CyclicBarrier cb = new CyclicBarrier(current);

        OrderService orderService = new OrderServiceImpl();

        for (int i=0; i<current;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"我准备好了！");
                    //等待一起触发
                    try {
                        cb.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
