package github.veikkoroc.test.zk.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/7 14:21
 */
public class TestCurator {
    @Test
    public void creatPath() throws Exception {
        //1、创建重试策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(2000,2);
        //2、创建CuratorFramework对象
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(6000)
                .retryPolicy(retryPolicy)
                .build();
        //3、开启启动
        curatorFramework.start();

        //4、创建节点
        curatorFramework.create().forPath("/vc", "Roc".getBytes());
        //多级节点
        curatorFramework.create().creatingParentsIfNeeded().forPath("/vc1/vc2","This is vc2".getBytes());
        //临时节点
        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/vc3","临时节点".getBytes());
        //5、关闭资源
        curatorFramework.close();

    }
    @Test
    public void getData() throws Exception {
        //1、创建重传策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(2000,2);
        //2、创建CuratorFramework对象
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(6000)
                .connectString("127.0.0.1:2181")
                .build();
        //3、开启启动
        curatorFramework.start();
        //4、获取节点信息
        byte[] bytes = curatorFramework.getData().forPath("/vc");
        System.out.println(new String(bytes));
        byte[] bytes1 = curatorFramework.getData().forPath("/vc1/vc2");
        System.out.println(new String(bytes1));
        //5、关闭资源
        curatorFramework.close();

    }

    @Test
    public void watch() throws Exception {
        //1、创建重传策略
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(2000,2);
        //2、创建CuratorFramework对象
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(6000)
                .connectString("127.0.0.1:2181")
                .build();
        //3、开启启动
        curatorFramework.start();
        //4、创建缓存节点、第三个参数为是否压缩,/testWatch实际不存在
        NodeCache nodeCache = new NodeCache(curatorFramework,"/testWatch",false);
        nodeCache.start(true);
        //开始监听
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("节点："+nodeCache.getCurrentData().getPath());
                System.out.println("数据："+new String(nodeCache.getCurrentData().getData()));
            }
        });
        Thread.sleep(2000);
        curatorFramework.create().forPath("/testWatch","Watch Test".getBytes());
        Thread.sleep(2000);
        curatorFramework.setData().forPath("/testWatch","北京".getBytes());
        Thread.sleep(2000);
        curatorFramework.delete().forPath("/testWatch");
        Thread.sleep(2000);
        //关闭资源
        curatorFramework.close();
    }
}
