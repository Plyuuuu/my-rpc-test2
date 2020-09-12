package github.veikkoroc.registry.zk_impl;

import github.veikkoroc.registry.ServiceRegistry;
import github.veikkoroc.utils.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/8 9:25
 */
@Slf4j
public class ZkServiceRegister implements ServiceRegistry {

    @Override//   github.veikkoroc.service.UserService11.0,   //192.168.1.101:9998
    public void registryService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        //“/my-rpc/toRpcServiceName()/IP:端口号”--->/my-rpc/github.veikkoroc.service.UserService11.0/192.168.1.101:9998
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
    @Test
    public void test01(){
       /* try {
           // System.out.println(new InetAddress());报错
            System.out.println(InetAddress.getLocalHost());                     //DESKTOP-472TN60/192.168.1.101
            System.out.println(InetAddress.getLocalHost().getHostAddress());    //192.168.1.101
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
        try {
            System.out.println(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),9998).toString());
            String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + "rpcServiceName" + new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(),9998).toString();
            System.out.println(servicePath);
            //在注册中心创建永久节点
            CuratorFramework zkClient = CuratorUtils.getZkClient();
            CuratorUtils.createPersistentNode(zkClient, servicePath);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // registryService("rpcServiceName",new InetSocketAddress(9998));
    }
}

