package github.veikkoroc.remoterpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 19:46
 */
public class RemoteInvocationHandler implements InvocationHandler {
    String host;
    int port;

    public RemoteInvocationHandler() {
    }

    public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        RPCNetTransport rpcNetTransport = new RPCNetTransport(host, port);

        return rpcNetTransport.sendRequest(rpcRequest);
    }
}
