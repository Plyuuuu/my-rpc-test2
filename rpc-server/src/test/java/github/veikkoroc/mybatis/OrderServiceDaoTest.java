package github.veikkoroc.mybatis;

import github.veikkoroc.entry.Order;
import github.veikkoroc.service.dao.OrderServiceDao;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/27 13:35
 */
public class OrderServiceDaoTest {
    public static void main(String[] args) {
        OrderServiceDao orderServiceDao = new OrderServiceDao();
        Order orderById = orderServiceDao.getOrderById(1);
        System.out.println(orderById);
    }
}
