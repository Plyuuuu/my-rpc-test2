package github.veikkoroc.test.proxy.jdkProxy2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/13 14:10
 */
public class MyProxy implements InvocationHandler {



    /**
     * 代理类中的真实对象
     */
    private final Object target;
    /**
     * 构造器
     */
    public MyProxy(Object target) {
        this.target = target;
    }
    /**
     * 根据接口名获取代理
     */
    public Object getProxyInstance(){
        return  Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{SendMessage.class},this);
    }

    /**
     * 加强方法
     * @param proxy   代理类目标对象
     * @param method  目标方法
     * @param args    目标方法的参数
     * @return        代理类
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用方法前
        System.out.println("before method " + method.getName());
        //执行目标方法
        Object result = method.invoke((SendMessage)target, args);
        //调用方法之后，我们同样可以添加自己的操作
        System.out.println("after method " + method.getName());
        return result;
 }
    /**
     * 获取自动生成的class文件
     */
    /*public void getProxyClass(){
        byte[] bytes = ProxyGenerator.generateProxyClass("TestInterface$proxy", new Class[]{TestInterface.class});
        OutputStream os = null;
        try {
            os = new FileOutputStream("test-code/t.class");
            os.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

}
