package com.zookeeper.zookeeperdistribut.lock;

import com.zookeeper.zookeeperdistribut.zk.ZKDistributeLock;

import java.util.concurrent.locks.Lock;

/**
 * @Author: LinSong
 * @Date: 2019/4/15 15:24
 */
public class OrderServiceImplWithLock implements OrderService{
    //用static修饰来模拟公用一个订单编号服务

    private static OrderCodeGenerator ocg = new OrderCodeGenerator();

    //创建订单接口

    public void createOrder(){
        String orderCode= null;
        //分布式锁
        Lock lock = new ZKDistributeLock("/study888");
        try {
            lock.lock();
            //获取订单号
            orderCode = ocg.getOrderCode();
        }finally {

        }

    }


}
