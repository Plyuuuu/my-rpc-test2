package github.veikkoroc.test.dubboRPC.publicInterface;

/**
 * 接口：公用部分，服务提供者和消费者都需要
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/9 11:04
 */
public interface HelloService {
    String hello(String mes);
}
