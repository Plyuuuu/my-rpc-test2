package github.veikkoroc.test.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/6 20:31
 */
public class TestZookeeper {
    //Zookeeper 的参数
    //String connectString:Zookeeper节点(哪个ZK集群)，多个节点用逗号分隔
    private String connectString = "127.0.0.1:2181";
    //sessionTimeout:会话超时时间,毫秒
    private int sessionTimeout = 3000;
    //Watcher：监听器
    Watcher watcher = new Watcher() {
        @Override
        public void process(WatchedEvent watchedEvent) {
            System.out.println("==============Start==============");
            List<String> children = null;
            try {
                children = zooKeeper.getChildren("/", true);
                for (String child : children) {
                    System.out.println(child);
                }
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("==============End==============");
        }
    };

    ZooKeeper zooKeeper;


    @Before
    public void init(){
        try {
            zooKeeper = new ZooKeeper(connectString,sessionTimeout,watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //创建节点
    @Test
    public void createNode(){
        try {
            String s = zooKeeper.create("/veikkoroc", "VeikkoRoc".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(s);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //获取子节点，并监控节点的变化
    @Test
    public void getDataAndWatch() throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    //判断节点是否存在
    @Test
    public void exit() throws KeeperException, InterruptedException {
        Stat exists = zooKeeper.exists("/dubbo", false);
        System.out.println(exists==null?"No exists":"Exists");
    }

}
