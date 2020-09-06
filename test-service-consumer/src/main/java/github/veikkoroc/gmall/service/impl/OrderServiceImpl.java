package github.veikkoroc.gmall.service.impl;

import github.veikkoroc.gmall.bean.UserAddress;
import github.veikkoroc.gmall.service.OrderService;
import github.veikkoroc.gmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/6 11:54
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    UserService userService;
    @Override
    public void initOrder(String userId) {
        System.out.println("UserId:"+userId);
        //1、查询用户收货地址
        List<UserAddress> userAddressList = userService.getUserAddressList(userId);
        for (UserAddress address : userAddressList) {
            System.out.println(address.getUserAddress());
        }
    }
}
