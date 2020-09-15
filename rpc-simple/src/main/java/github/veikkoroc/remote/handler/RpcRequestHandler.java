package github.veikkoroc.remote.handler;

import github.veikkoroc.factory.SingletonFactory;
import github.veikkoroc.provider.ServiceProvider;
import github.veikkoroc.provider.impl.ServiceProviderImpl;
import github.veikkoroc.remote.entry.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * RpcRequest 处理器
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 11:25
 */
@Slf4j
public class RpcRequestHandler {
    //获得服务提供者
    private final ServiceProvider serviceProvider;

    public RpcRequestHandler() {
        this.serviceProvider = SingletonFactory.getInstance(ServiceProviderImpl.class);
    }

    /**
     * 返回请求调用的目标方法
     * @param rpcRequest
     * @return
     */
    public Object handle(RpcRequest rpcRequest){
        Object service = serviceProvider.getService(rpcRequest.toRpcServiceProperties());
        log.info("==========根据RpcRequest获得的服务:[{}]",service);
        return invokeTargetMethod(rpcRequest,service);

    }

    /**
     *获取方法执行的结果
     * @param rpcRequest    客户端请求
     * @param service       服务对象
     * @return 目标方法执行结果
     */
    private Object invokeTargetMethod(RpcRequest rpcRequest,Object service){
        Object result =null;
        try{
            //根据目标方法名和参数获得方法对象
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());//public java.lang.String github.veikkoroc.remote.entry.RpcRequest.getMethodName()
            log.info("==========目标方法：[{}]",method);

            result = method.invoke(service, rpcRequest.getParameters());

            log.info("==========服务:[{}] 成功调用方法: [{}]",rpcRequest.getInterfaceName(),rpcRequest.getMethodName());

        }catch (NoSuchMethodException | IllegalArgumentException | InvocationTargetException | IllegalAccessException e){
            throw new RuntimeException("=========调用目标方法失败",e);
        }
        return result;
    }
}
