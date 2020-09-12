package github.veikkoroc.remoterpc;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 18:01
 */
public class HelloServiceImpl implements IHelloService {
    @Override
    public String saveUser(User user) {
        System.out.println("User :"+user);
        return "Success";
    }

    @Override
    public String sayHello(String content) {
        return  "Hello world:"+content;
    }
}
