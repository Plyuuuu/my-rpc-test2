package github.veikkoroc.test.proxy.jdkProxy;

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
}
