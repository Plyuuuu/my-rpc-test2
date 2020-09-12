package github.veikkoroc.test.proxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 16:21
 */
//必须实现InvocationHandler接口，完成代理类要做的功能（1、调用目标方法 2、增强功能）
public class MyHandler implements InvocationHandler {
    //代理类的目标对象
    private final Object target;
    //动态代理：目标对象是活动的，不是规定的，需要传入进来
    //传入是谁，就给谁创建代理
    public MyHandler(Object target){
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用方法前
        System.out.println("before method " + method.getName());
        //执行目标方法
        Object result = method.invoke(target, args);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method " + method.getName());
        return result;
    }
}
