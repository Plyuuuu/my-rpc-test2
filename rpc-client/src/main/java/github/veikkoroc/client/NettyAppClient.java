package github.veikkoroc.client;

import github.veikkoroc.entry.User;
import github.veikkoroc.proxy.RpcClientProxy;
import github.veikkoroc.remote.entry.RpcServiceProperties;
import github.veikkoroc.remote.transport.netty.client.NettyClientTransport;
import github.veikkoroc.service.UserService;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 15:04
 */
public class NettyAppClient {
    public static void main(String[] args) {
        //数据传输对象
        NettyClientTransport nettyClientTransport = new NettyClientTransport();
        //服务的属性
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder().group("1").version("1.0").build();
        //获得代理对象
        RpcClientProxy rpcClientProxy = new RpcClientProxy(nettyClientTransport, rpcServiceProperties);

        UserService userService = rpcClientProxy.getProxy(UserService.class);
        User userById = userService.getUserById(1);
        System.out.println(userById);

    }

}
