package com.zookeeper.zookeeperdistribut.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author: LinSong
 * @Date: 2019/4/15 15:19
 */
public class ConcurrentTestDistributeDemo {

    public static void main(String[] args) {
        //并发数量
        int currency = 3;

        //循环屏障
        CyclicBarrier cb = new CyclicBarrier(currency);

        //多线程模拟高并发
        for(int i=0;i<currency; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //模拟分布式集群的场景
                    OrderService orderService = new OrderServiceImplWithLock();

                    System.out.println(Thread.currentThread().getName()+"-------我准备好了~~");
                    //等待一起触发
                    try {
                        cb.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    //调用创建订单服务
                    orderService.createOrder();
                }
            }).start();

        }
    }

}
