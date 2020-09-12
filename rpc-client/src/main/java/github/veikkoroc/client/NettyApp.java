package github.veikkoroc.client;

import github.veikkoroc.entry.User;
import github.veikkoroc.proxy.RpcClientProxy;
import github.veikkoroc.remote.entry.RpcServiceProperties;
import github.veikkoroc.remote.transport.netty.client.NettyClientTransport;
import github.veikkoroc.service.UserService;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 15:04
 */
public class NettyApp {
    public static void main(String[] args) {
        NettyClientTransport rpcClient = new NettyClientTransport();
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder().group("1").version("1.0").build();

        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcClient, rpcServiceProperties);

        UserService userService = rpcClientProxy.getProxy(UserService.class);
        User userById = userService.getUserById(1);
        System.out.println(userById);

    }
}
