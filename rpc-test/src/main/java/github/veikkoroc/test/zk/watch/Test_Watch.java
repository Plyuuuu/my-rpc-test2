package github.veikkoroc.test.zk.watch;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/7 12:21
 */
public class Test_Watch {
    @Test
    public void test01() throws InterruptedException {
        //创建客户端
        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        //创建节点
        zkClient.create("/test","我是测试数据", CreateMode.PERSISTENT);
        //使用“Watch”监控
        zkClient.subscribeDataChanges("/test", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("值被修改：路径："+s+"    值："+o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("目录被删除：路径："+s);
            }
        });
        //修改数据
        zkClient.writeData("/test","我被修改了");
        System.out.println(zkClient.readData("/test").toString());
        Thread.sleep(2000);
        //删除数据
        boolean delete = zkClient.delete("/test");
        System.out.println("是否删除成功？"+delete);
        Thread.sleep(2000);

        zkClient.close();
    }
}
