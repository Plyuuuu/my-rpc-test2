package github.veikkoroc.service.impl;

import github.veikkoroc.entry.Order;
import github.veikkoroc.service.OrderService;
import github.veikkoroc.service.dao.OrderServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/27 12:06
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    @Qualifier(value = "orderServiceDao")
    OrderServiceDao orderServiceDao;

    @Override
    public Order getOrderById(int id) {
        if (orderServiceDao==null){
            throw new RuntimeException("OrderServiceImpl的orderServiceDao属性注入失败");
        }
        return orderServiceDao.getOrderById(id);
    }
}
