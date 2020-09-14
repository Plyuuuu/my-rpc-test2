package github.veikkoroc.test.proxy.jdkProxy;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 16:19
 */
public class SendMessageImpl implements SendMessage {
    @Override
    public String sendMessage(String message) {
        System.out.println("send message:" + message);
        return message;
    }
    @Test
    public void test(){
        System.out.println(SendMessage.class);
        Class<?>[] classes = {SendMessage.class};
        System.out.println(Arrays.toString(classes));
        System.out.println(SendMessageImpl.class);
        Class<?>[] classes1 = {SendMessageImpl.class};
        System.out.println(Arrays.toString(classes1));
    }
}
