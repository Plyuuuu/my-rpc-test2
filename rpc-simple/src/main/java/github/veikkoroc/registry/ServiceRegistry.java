package github.veikkoroc.registry;

import java.net.InetSocketAddress;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/4 11:02
 */
public interface ServiceRegistry {
    /**
     *注册服务，通过服务名，服务地址
     * @param rpcServiceName service name
     * @param inetSocketAddress service address
     */
    void registryService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
