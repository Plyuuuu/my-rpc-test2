package github.veikkoroc.remoterpc;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 20:06
 */
public class App {
    public static void main(String[] args) {
        RpcClientProxy proxy = new RpcClientProxy();
        IHelloService iHelloService = proxy.clientProxy(IHelloService.class, "localhost", 8888);
        System.out.println(iHelloService.sayHello(" M i c "));
    }
}
