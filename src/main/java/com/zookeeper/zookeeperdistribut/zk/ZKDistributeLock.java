package com.zookeeper.zookeeperdistribut.zk;

import com.zookeeper.zookeeperdistribut.lock.MyZkSerializer;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import sun.dc.pr.PRError;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author: LinSong
 * @Date: 2019/4/15 14:53
 */
public class ZKDistributeLock implements Lock {
    private String lockPath;

    private ZkClient client;

    public ZKDistributeLock(String lockPath) {
        super();
        this.lockPath = lockPath;
        this.client = client;
        client = new ZkClient("localhost:2181");
        client.setZkSerializer(new MyZkSerializer());
    }
    @Override
    public boolean tryLock() {//不会阻塞
        //创建节点
        try {
            client.createEphemeral(lockPath);
        }catch (ZkNodeExistsException e){
            return false;
        }
        return true;
    }

    @Override
    public void unlock() {
        client.delete(lockPath);
    }

    @Override
    public void lock() {//如果获取不到锁 阻塞等待
        if(!tryLock()){
            //没获取锁 阻塞自己
            waitForLock();
        }
    }
    private void waitForLock(){
        CountDownLatch cdl = new CountDownLatch(1);
        IZkDataListener listener = new IZkDataListener(){
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
            }
            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("-----收到节点被删除了------");
                cdl.countDown();
            }
        };
        client.subscribeDataChanges(lockPath,listener);

        //阻塞自己
        if(this.client.exists(lockPath)){
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //取消注册
        client.unsubscribeDataChanges(lockPath,listener);


    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }


    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }
}
