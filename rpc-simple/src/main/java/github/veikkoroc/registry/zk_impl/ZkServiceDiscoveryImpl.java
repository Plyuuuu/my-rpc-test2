package github.veikkoroc.registry.zk_impl;

import github.veikkoroc.loadbalance.LoadBalance;
import github.veikkoroc.loadbalance.impl.LoadBalanceImpl;
import github.veikkoroc.registry.ServiceDiscovery;
import github.veikkoroc.utils.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 15:24
 */
@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {
    //负载均衡策略
    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        this.loadBalance = new LoadBalanceImpl();
    }

    @Override
    public InetSocketAddress lookupService(String rpcServiceName) {
        //获得Zk客户端
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        //serviceUrlList中存放的是子节点的名称，如：192.168.1.103:6668等等
        List<String> childrenNodes = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        if (childrenNodes.size()==0){
            throw new RuntimeException("服务未找到："+rpcServiceName);
        }
        //负载均衡
        String s = loadBalance.selectServiceAddress(childrenNodes);
        log.info("成功找到服务器地址：[{}]",s);
        //获得服务器地址的 ip 和 port
        String[] split = s.split(":");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);
        return new InetSocketAddress(ip,port);
    }
}
