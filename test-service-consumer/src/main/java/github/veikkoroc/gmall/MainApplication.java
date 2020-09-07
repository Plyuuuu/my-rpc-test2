package github.veikkoroc.gmall;

import github.veikkoroc.gmall.service.OrderService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/6 13:43
 */
public class MainApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("consumer.xml");
        OrderService bean = ioc.getBean(OrderService.class);
        bean.initOrder("1");
        try {
            Thread.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
