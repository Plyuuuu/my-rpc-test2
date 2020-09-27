package github.veikkoroc;

import github.veikkoroc.remote.entry.RpcServiceProperties;
import github.veikkoroc.remote.transport.netty.server.NettyServer;
import github.veikkoroc.service.impl.OrderServiceImpl;
import github.veikkoroc.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/27 14:19
 */
@Slf4j
@Configuration
@ComponentScan("github.veikkoroc")
public class MyRPCServer2 {
    public static void main(String[] args) {
        //获取需要暴露的服务
        AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(MyRPCServer2.class);
        UserServiceImpl userServiceImpl = ioc.getBean("userServiceImpl", UserServiceImpl.class);
        OrderServiceImpl orderServiceImpl = ioc.getBean("orderServiceImpl", OrderServiceImpl.class);

        log.info("即将暴露服务1:[{}]",userServiceImpl);
        log.info("即将暴露服务2:[{}]",orderServiceImpl);

        NettyServer nettyServer = ioc.getBean("nettyServer", NettyServer.class);

        log.info("获得服务器实体[{}]",nettyServer);

        //手动注册服务
        //1.设置服务属性,属性name后面根据服务获取
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder().version("1.0").group("1").build();
        //2.注册服务
        nettyServer.registerService(userServiceImpl,rpcServiceProperties);
        nettyServer.registerService(orderServiceImpl,rpcServiceProperties);

        //启动服务器
        nettyServer.startNettyServer();

    }

}
