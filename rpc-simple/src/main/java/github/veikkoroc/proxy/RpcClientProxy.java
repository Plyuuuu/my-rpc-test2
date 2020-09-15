package github.veikkoroc.proxy;

import github.veikkoroc.remote.entry.RpcMessageChecker;
import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.remote.entry.RpcResponse;
import github.veikkoroc.remote.entry.RpcServiceProperties;
import github.veikkoroc.remote.transport.ClientTransport;
import github.veikkoroc.remote.transport.netty.client.NettyClientTransport;
import github.veikkoroc.remote.transport.socket.SocketRpcClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * 动态代理类。 当动态代理对象调用方法时，它实际上将调用以下invoke方法。
 * 正是由于动态代理，客户端调用的远程方法就像调用本地方法一样（中间过程被屏蔽）
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 21:20
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {
    /**
     *   用于向服务器发送请求。
     */
    private final ClientTransport clientTransport;
    /**
     * 服务属性
     */
    private final RpcServiceProperties rpcServiceProperties;

    public RpcClientProxy(ClientTransport clientTransport, RpcServiceProperties rpcServiceProperties) {
        this.clientTransport = clientTransport;
        if (rpcServiceProperties.getGroup() == null) {
            rpcServiceProperties.setGroup("");
        }
        if (rpcServiceProperties.getVersion() == null) {
            rpcServiceProperties.setVersion("");
        }
        this.rpcServiceProperties = rpcServiceProperties;
    }

    public RpcClientProxy(ClientTransport clientTransport) {
        this.clientTransport = clientTransport;
        this.rpcServiceProperties = RpcServiceProperties.builder().group("").version("").build();
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz},this );
    }
    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.info("==========调用方法：[{}]",method.getName());
        log.info("==========传入参数：[{}]",args.toString());
        log.info("==========接口名字：[{}]",method.getDeclaringClass().getName());
        log.info("==========参数类型：[{}]",method.getParameterTypes());
        log.info("==========请求的ID：[{}]",UUID.randomUUID().toString());
        log.info("==========组：[{}]",rpcServiceProperties.getGroup());
        log.info("==========版本：[{}]",rpcServiceProperties.getVersion());


        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .interfaceName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .group(rpcServiceProperties.getGroup())
                .version(rpcServiceProperties.getVersion())
                .build();
        RpcResponse<Object> rpcResponse = null;
        //Netty传输
        if (clientTransport instanceof NettyClientTransport) {
            CompletableFuture<RpcResponse<Object>> completableFuture = (CompletableFuture<RpcResponse<Object>>) clientTransport.sendRpcRequest(rpcRequest);
            log.info("==========completableFuture:[{}]",completableFuture);
            log.info("==========completableFuture.get():[{}]",completableFuture.get());
            // completableFuture.get()会一直阻塞直到 Future 完成。
            rpcResponse = completableFuture.get();
        }
        //Socket 传输
        if (clientTransport instanceof SocketRpcClient) {
            rpcResponse = (RpcResponse<Object>) clientTransport.sendRpcRequest(rpcRequest);
        }
        RpcMessageChecker.check(rpcResponse, rpcRequest);
        return rpcResponse.getData();
    }
}
