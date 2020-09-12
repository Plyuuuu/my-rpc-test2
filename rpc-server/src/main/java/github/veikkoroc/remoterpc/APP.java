package github.veikkoroc.remoterpc;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 19:31
 */
public class APP {
    public static void main(String[] args) {
        IHelloService iHelloService = new HelloServiceImpl();
        RpcServerProxy rpcServerProxy = new RpcServerProxy();
        rpcServerProxy.publish(iHelloService,8888);

    }
}
