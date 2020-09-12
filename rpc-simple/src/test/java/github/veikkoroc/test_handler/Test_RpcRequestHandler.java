package github.veikkoroc.test_handler;

import github.veikkoroc.factory.SingletonFactory;
import github.veikkoroc.provider.ServiceProvider;
import github.veikkoroc.provider.impl.ServiceProviderImpl;
import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.remote.handler.RpcRequestHandler;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 13:06
 */
public class Test_RpcRequestHandler {
    public static void main(String[] args) {
        //创建服务提供者
        //ServiceProvider serviceProvider   = SingletonFactory.getInstance(ServiceProviderImpl.class);

        //创建request请求
        RpcRequest rpcRequest = RpcRequest.builder().requestId("1").methodName("方法名").interfaceName("github.veikkoroc.service.UserService").group("1").version("1.0") .build();


        RpcRequestHandler rpcRequestHandler = new RpcRequestHandler();
        Object handle = rpcRequestHandler.handle(rpcRequest);
        System.out.println(handle);


    }
}
