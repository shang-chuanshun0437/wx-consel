package com.weiyi.wx.order.common.zk;

import org.apache.zookeeper.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestZK {
    private ZooKeeper zooKeeper = null;
    CountDownLatch countDownLatch = new CountDownLatch(1);
    private static String host = "192.168.1.7:2181,192.168.1.7:2182,192.168.1.7:2183";
    public void connect(){
        try {
            System.out.println("begin connect");
            zooKeeper = new ZooKeeper(host,2000,new BaseWachter());
            zooKeeper.create("/testRootPath2", "testRootData2".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
            List<String> child = zooKeeper.getChildren("/testRootPath2",new BaseWachter());
            //zooKeeper.getData();
            //zooKeeper.exists();
            if (child.size() > 0){
                System.out.println("child is not null,");
            }
            zooKeeper.create("/testRootPath2/testChild2","testChild2".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            countDownLatch.await();
            System.out.println("connect");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    class BaseWachter implements Watcher{

        @Override
        public void process(WatchedEvent watchedEvent) {
            if (watchedEvent.getState() == Event.KeeperState.SyncConnected){
                System.out.println("watcher receive state:syncConnected.");
                countDownLatch.countDown();
            }
            if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
                System.out.println("EventType.NodeChildrenChanged.");
            }
        }
    }

    public static void main(String []args){
        TestZK testZK = new TestZK();
        testZK.connect();
    }
}

