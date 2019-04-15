package com.zookeeper.zookeeperdistribut.lock;

/**
 * @Author: LinSong
 * @Date: 2019/4/15 16:48
 */
public class OrderServiceImpl implements OrderService {

    private OrderCodeGenerator ocg = new OrderCodeGenerator();

    //创建订单接口
    public void createOrder(){
        //获取订单号
        String orderCode = ocg.getOrderCode();

        System.out.println(Thread.currentThread().getName()+"==================="+orderCode);
    }

}
