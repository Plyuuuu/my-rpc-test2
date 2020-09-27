package github.veikkoroc.mybatis;

import github.veikkoroc.entry.Order;
import github.veikkoroc.service.dao.OrderServiceDao;
import github.veikkoroc.service.impl.OrderServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/27 13:49
 */
@Configuration
@ComponentScan("github.veikkoroc.service")
public class OrderServiceImplTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(OrderServiceImplTest.class);
        OrderServiceDao orderServiceDao = ioc.getBean("orderServiceDao", OrderServiceDao.class);

        System.out.println(orderServiceDao);

        OrderServiceImpl orderServiceImpl = ioc.getBean("orderServiceImpl", OrderServiceImpl.class);
        System.out.println(orderServiceImpl.getOrderById(1));
    }
}
