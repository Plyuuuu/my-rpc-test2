package github.veikkoroc.provider;

import github.veikkoroc.remote.entry.RpcServiceProperties;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/8 9:20
 */
public interface ServiceProvider {
    /**
     * @param service              服务对象
     * @param serviceClass         由服务实例对象实现的接口类
     * @param rpcServiceProperties 服务相关属性
     */
    void addService(Object service, Class<?> serviceClass, RpcServiceProperties rpcServiceProperties);

    /**
     * @param rpcServiceProperties 服务相关的属性
     * @return service object
     */
    Object getService(RpcServiceProperties rpcServiceProperties);

    /**
     * @param service              服务对象
     * @param rpcServiceProperties 服务相关属性
     */
    void publishService(Object service, RpcServiceProperties rpcServiceProperties);

    /**
     * @param service 服务对象
     */
    void publishService(Object service);
}
