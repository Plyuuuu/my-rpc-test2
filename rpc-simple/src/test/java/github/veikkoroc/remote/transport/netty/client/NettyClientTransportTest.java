package github.veikkoroc.remote.transport.netty.client;

import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.remote.entry.RpcResponse;

import java.util.concurrent.CompletableFuture;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/15 11:11
 */
public class NettyClientTransportTest {
    public static void main(String[] args) {
        NettyClientTransport nettyClientTransport = new NettyClientTransport();
        RpcRequest rpcRequest = RpcRequest.builder().requestId("123").interfaceName("github.veikkoroc.service.UserService").group("1").version("1.0").build();
        CompletableFuture<RpcResponse<Object>> rpcResponseCompletableFuture = nettyClientTransport.sendRpcRequest(rpcRequest);
        System.out.println(rpcResponseCompletableFuture);
    }
}
