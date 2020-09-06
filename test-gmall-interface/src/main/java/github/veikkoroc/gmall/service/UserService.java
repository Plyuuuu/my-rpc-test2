package github.veikkoroc.gmall.service;

import github.veikkoroc.gmall.bean.UserAddress;

import java.util.List;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/6 11:43
 */
public interface UserService {
    public List<UserAddress> getUserAddressList(String userId);
}
