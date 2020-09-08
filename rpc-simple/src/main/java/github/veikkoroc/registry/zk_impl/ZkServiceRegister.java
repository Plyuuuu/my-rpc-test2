package github.veikkoroc.registry.zk_impl;

import github.veikkoroc.registry.ServiceRegistry;
import github.veikkoroc.utils.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/8 9:25
 */
@Slf4j
public class ZkServiceRegister implements ServiceRegistry {

    @Override
    public void registryService(String rpcServiceName, InetSocketAddress inetSocketAddress) {
        //“/my-rpc/toRpcServiceName()/IP:端口号”
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + rpcServiceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
}

