package github.veikkoroc.remoterpc;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 17:59
 */
public interface IHelloService {

    String saveUser(User user);

    String sayHello(String content);
}
