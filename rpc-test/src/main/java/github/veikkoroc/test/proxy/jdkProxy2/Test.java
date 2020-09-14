package github.veikkoroc.test.proxy.jdkProxy2;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/13 14:25
 */
public class Test {
    public static void main(String[] args) {
       MyProxy myProxy = new MyProxy(new SendMessageImpl());
        System.out.println(myProxy.toString());
        Object proxyInstance = myProxy.getProxyInstance();
        System.out.println(proxyInstance);


    }
}
