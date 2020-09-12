package github.veikkoroc;

import github.veikkoroc.provider.impl.ServiceProviderImpl;
import github.veikkoroc.remote.entry.RpcServiceProperties;
import github.veikkoroc.remote.transport.netty.server.NettyServer;
import github.veikkoroc.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/11 16:02
 */
@ComponentScan("github.veikkoroc")
public class NettyApp {
    public static void main(String[] args) {
        // 1、在Zookeeper注册中心暴露服务
        //把当前类单xml配置类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(NettyApp.class);
        NettyServer nettyServer = annotationConfigApplicationContext.getBean(NettyServer.class);
       // System.out.println(nettyServer);

        //创建服务UserServiceImpl
        //UserServiceImpl userService = new UserServiceImpl();
        UserServiceImpl userService = annotationConfigApplicationContext.getBean(UserServiceImpl.class);
        //System.out.println(userService);

        //手动配置Rpc服务属性
        //.serviceName("")后面根据UserService动态获取
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder().group("1").version("1.0").build();

        //手动注册服务UserServiceImpl
        nettyServer.registerService(userService,rpcServiceProperties);


        //启动服务器
        nettyServer.startNettyServer();
    }



   /* @Test
    public void test01(){
        ServiceProviderImpl serviceProvider = new ServiceProviderImpl();
        serviceProvider.testPublishService(new UserServiceImpl());
    }*/
}
