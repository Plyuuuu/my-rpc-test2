package github.veikkoroc.provider.impl;

import github.veikkoroc.provider.ServiceProvider;
import github.veikkoroc.registry.ServiceRegistry;
import github.veikkoroc.registry.zk_impl.ZkServiceRegister;
import github.veikkoroc.remote.entry.RpcServiceProperties;

import github.veikkoroc.remote.transport.netty.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
     *      Rpc 服务名（接口名+版本+组）
     * value: service object
     *      服务对象
     */
    //serviceMap缓存
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

        //return this.getServiceName()+this.getGroup()+this.getVersion();
        String rpcServiceName = rpcServiceProperties.getRpcServicePropertiesFields();

        //registeredService是一个Set<String>,服务已经注册就不再添加
        if (registeredService.contains(rpcServiceName)) {
            return;
        }
        //服务还没有注册,添加到map缓存
        registeredService.add(rpcServiceName);
        serviceMap.put(rpcServiceName, service);
        log.info("===========暴露服务: [{}] 和接口:{}", rpcServiceName, service.getClass().getInterfaces());
    }

    @Override
    public Object getService(RpcServiceProperties rpcServiceProperties) {
        log.info("==========缓存serviceMap中的服务：[{}]",serviceMap);
        Object service = serviceMap.get(rpcServiceProperties.getRpcServicePropertiesFields());
        if (null == service) {
            throw new RuntimeException("没有找到指定的服务");
        }
        return service;
    }

   /* @Test
    public void testGetService(){

    }*/



    @Override
    public void publishService(Object service) {
        this.publishService(service, RpcServiceProperties.builder().group("").version("").build());
    }

    @Override
    public void publishService(Object service, RpcServiceProperties rpcServiceProperties) {

            log.info("===========要注册的服务[{}] 服务的属性[{}]",service,rpcServiceProperties);
            String host = "127.0.0.1";//InetAddress.getLocalHost().getHostAddress();//127.0.0.1

            //获取服务对象的接口 interface github.veikkoroc.service.UserService
            Class<?> serviceRelatedInterface = service.getClass().getInterfaces()[0];

            //返回 Java Language Specification 中所定义的底层类的规范化名称。github.veikkoroc.service.UserService
            String serviceName = serviceRelatedInterface.getCanonicalName();

            //设置服务的名称，其实就是接口的名称github.veikkoroc.service.UserService
            rpcServiceProperties.setServiceName(serviceName);
            log.info("===========服务的接口 [{}] 服务的属性[{}] ",serviceRelatedInterface,rpcServiceProperties);
            //将服务加入本地缓存中
            this.addService(service, serviceRelatedInterface, rpcServiceProperties);
            log.info("===========查看本地缓存[{}]",serviceMap);
            //将服务注册到注册中心
            //   github.veikkoroc.service.UserService11.0,   //192.168.1.101:9998
            serviceRegistry.registryService(rpcServiceProperties.getRpcServicePropertiesFields(), new InetSocketAddress(host, NettyServer.port));

    }









   /* @Test
    public void testPublishService(Object service){
        //Object service = new UserServiceImpl();//服务对象
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder().serviceName("Test").group("1").version("1.0").build();
        System.out.println("service: "+service);
        System.out.println("rpcServiceProperties: "+rpcServiceProperties);
        try {
            String host = InetAddress.getLocalHost().getHostAddress();//127.0.0.1
            Class<?> serviceRelatedInterface = service.getClass().getInterfaces()[0];//获取服务对象的接口 interface github.veikkoroc.service.UserService
            String serviceName = serviceRelatedInterface.getCanonicalName();//获取规范名称 github.veikkoroc.service.UserService
            System.out.println("host: "+host);
            System.out.println("serviceRelatedInterface: "+serviceRelatedInterface);
            System.out.println("serviceName: "+serviceName);

            System.out.println(rpcServiceProperties.getRpcServicePropertiesFields());
            System.out.println(new InetSocketAddress(host, port));//192.168.1.101:9998
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }*/
    @Test
    public void testLog4j(){
        log.info("测试Log");
    }
}
