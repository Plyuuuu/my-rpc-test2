package github.veikkoroc.registry;

import github.veikkoroc.extension.SPI;

import java.net.InetSocketAddress;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/4 11:02
 */
@SPI
public interface ServiceDiscovery {
    /**
     *通过服务名称查找服务
     * @param RpcServiceName
     * @return  service address
     */
    InetSocketAddress lookupService(String RpcServiceName);
}
