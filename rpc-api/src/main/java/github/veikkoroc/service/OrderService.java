package github.veikkoroc.service;

import github.veikkoroc.entry.Order;

import java.util.List;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/27 12:01
 */
public interface OrderService {
    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    public Order getOrderById(int id);
}
