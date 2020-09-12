package github.veikkoroc.remoterpc;

import java.lang.reflect.Proxy;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 19:36
 */
public class RpcClientProxy {
    //动态代理
    public <T> T clientProxy(Class<T> interfaceCls,String host,int port){
        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[] {interfaceCls},
                new RemoteInvocationHandler(host,port));
    }
}
