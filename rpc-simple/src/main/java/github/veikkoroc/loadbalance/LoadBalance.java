package github.veikkoroc.loadbalance;

import java.util.List;

/**
 * 负载均衡策略
 *
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 15:27
 */
public interface LoadBalance {
    /**
     * 从现有的服务器选一个连接
     * @param serviceAddresses  所有服务器地址
     * @return  选择的服务器地址
     */
    String selectServiceAddress(List<String> serviceAddresses);
}
