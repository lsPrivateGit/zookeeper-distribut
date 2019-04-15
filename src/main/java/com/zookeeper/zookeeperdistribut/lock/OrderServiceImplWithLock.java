package com.zookeeper.zookeeperdistribut.lock;

import com.zookeeper.zookeeperdistribut.zk.ZKDistributeLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: LinSong
 * @Date: 2019/4/15 15:24
 */
public class OrderServiceImplWithLock implements OrderService{
    //用static修饰来模拟公用一个订单编号服务

    private static OrderCodeGenerator ocg = new OrderCodeGenerator();

    private static Lock lock = new ReentrantLock();

    //创建订单接口

    @Override
    public void createOrder(){
        String orderCode= null;
        try {
            //分布式锁
            lock.lock();
            //获取订单编号
            orderCode = ocg.getOrderCode();
        }finally {
            lock.unlock();
        }

        System.out.println(Thread.currentThread().getName()+"==============="+orderCode);
/*
        Lock lock = new ZKDistributeLock("/study888");
*/
    }


}
