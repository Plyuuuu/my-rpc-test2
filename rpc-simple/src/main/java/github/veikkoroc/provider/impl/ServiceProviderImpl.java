package github.veikkoroc.provider.impl;

import github.veikkoroc.provider.ServiceProvider;
import github.veikkoroc.registry.ServiceRegistry;
import github.veikkoroc.registry.zk_impl.ZkServiceRegister;
import github.veikkoroc.remote.entry.RpcServiceProperties;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/8 9:22
 */
@Slf4j
public class ServiceProviderImpl implements ServiceProvider {

    /**
     * key: rpc service name(interface name + version + group)
     * value: service object
     */
    private final Map<String, Object> serviceMap;
    private final Set<String> registeredService;
    private final ServiceRegistry serviceRegistry;

    public ServiceProviderImpl() {
        serviceMap = new ConcurrentHashMap<>();
        registeredService = ConcurrentHashMap.newKeySet();
        serviceRegistry = new ZkServiceRegister();
    }

    @Override
    public void addService(Object service, Class<?> serviceClass, RpcServiceProperties rpcServiceProperties) {
        String rpcServiceName = rpcServiceProperties.getRpcServicePropertiesFields();
        //registeredService是一个Set<String>,服务已经注册就return
        if (registeredService.contains(rpcServiceName)) {
            return;
        }
        //服务还没有注册
        registeredService.add(rpcServiceName);
        serviceMap.put(rpcServiceName, service);
        log.info("Add service: {} and interfaces:{}", rpcServiceName, service.getClass().getInterfaces());
    }

    @Override
    public Object getService(RpcServiceProperties rpcServiceProperties) {
        Object service = serviceMap.get(rpcServiceProperties.getRpcServicePropertiesFields());
        if (null == service) {
            throw new RuntimeException("没有找到指定的服务");
        }
        return service;
    }

    @Override
    public void publishService(Object service) {
        this.publishService(service, RpcServiceProperties.builder().group("").version("").build());
    }

    @Override
    public void publishService(Object service, RpcServiceProperties rpcServiceProperties) {
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            Class<?> serviceRelatedInterface = service.getClass().getInterfaces()[0];
            String serviceName = serviceRelatedInterface.getCanonicalName();
            rpcServiceProperties.setServiceName(serviceName);
            //将服务加入registeredService（Set<String>）和serviceMap（Map<服务名称，Object>）中
            this.addService(service, serviceRelatedInterface, rpcServiceProperties);
            //将服务注册到注册中心
            serviceRegistry.registryService(rpcServiceProperties.getRpcServicePropertiesFields(), new InetSocketAddress(host, 9998));
        } catch (UnknownHostException e) {
            log.error("occur exception when getHostAddress", e);
        }
    }
}
