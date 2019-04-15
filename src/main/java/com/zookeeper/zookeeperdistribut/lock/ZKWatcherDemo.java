package com.zookeeper.zookeeperdistribut.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * @Author: LinSong
 * @Date: 2019/4/15 14:39
 */
public class ZKWatcherDemo {
    public static void main(String[] args) {
        ZkClient client = new ZkClient("localhost:2181");
        client.setZkSerializer(new MyZkSerializer());
        client.subscribeDataChanges("/make/a", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object data) throws Exception {
                System.out.println("--------------收到节点变化："+data+"----------");
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("--------------收到节点被删除了！---------------");
            }
        });

        try {
            Thread.sleep(1000*60*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
