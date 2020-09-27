package github.veikkoroc.spring;

import github.veikkoroc.service.dao.OrderServiceDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/27 14:03
 */
public class SpringTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-config.xml");
        OrderServiceDao orderServiceDao = ioc.getBean("orderServiceDao", OrderServiceDao.class);
        System.out.println(orderServiceDao);
    }
}
