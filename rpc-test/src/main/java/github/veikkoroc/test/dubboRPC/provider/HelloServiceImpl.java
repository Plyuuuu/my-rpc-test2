package github.veikkoroc.test.dubboRPC.provider;

import github.veikkoroc.test.dubboRPC.publicInterface.HelloService;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/9 11:05
 */
public class HelloServiceImpl implements HelloService {
    //当消费方调用该方法时，就返回一个结果
    @Override
    public String hello(String mes) {
        System.out.println("收到客户端消息："+mes);
        //根据mes返回不同的结果
        if (mes!=null){
            return "你好客户端，我已经收到你的消息["+mes+"]";
        }else {
            return "你好客户端，我已经收到你的消息";
        }

    }
}
