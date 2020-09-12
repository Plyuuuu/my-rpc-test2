package github.veikkoroc.test.proxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 16:28
 */
public class Test {
    public static void main(String[] args) {
        //1、创建目标对象
        SendMessage target = new SendMessageImpl();
        //2、创建InvocationHandler对象
        InvocationHandler invocationHandler = new MyHandler(target);
        //3、创建代理对象，使用proxy
        Object o = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), invocationHandler);
        SendMessage sendMessage = (SendMessage)o;
        //4、通过代理执行方法
        String java = sendMessage.sendMessage("Java");
        System.out.println("动态代理对象调用方法的返回值"+java);
    }
}
