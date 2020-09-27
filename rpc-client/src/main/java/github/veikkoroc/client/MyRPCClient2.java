package github.veikkoroc.client;

import github.veikkoroc.entry.Order;
import github.veikkoroc.entry.User;
import github.veikkoroc.proxy.RpcClientProxy;
import github.veikkoroc.remote.entry.RpcServiceProperties;
import github.veikkoroc.remote.transport.netty.client.NettyClientTransport;
import github.veikkoroc.service.OrderService;
import github.veikkoroc.service.UserService;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/27 17:00
 */
public class MyRPCClient2 {
    public static void main(String[] args) {
        //客户端请求传输对象
        NettyClientTransport nettyClientTransport = new NettyClientTransport();
        //配置访问的服务的属性
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder().version("1.0").group("1").build();
        //获取代理对象
        RpcClientProxy rpcClientProxy = new RpcClientProxy(nettyClientTransport,rpcServiceProperties);
        //获取代理对象
        UserService userService = rpcClientProxy.getProxy(UserService.class);
        OrderService orderService = rpcClientProxy.getProxy(OrderService.class);

        User userById = userService.getUserById(1);
        Order orderById = orderService.getOrderById(1);
        System.out.println("远程服务器返回调用结果："+userById);
        System.out.println("远程服务器返回调用结果："+orderById);


    }
}
