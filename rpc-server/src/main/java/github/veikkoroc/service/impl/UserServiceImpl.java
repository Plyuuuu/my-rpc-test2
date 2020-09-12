package github.veikkoroc.service.impl;

import github.veikkoroc.entry.User;
import github.veikkoroc.service.UserService;
import org.springframework.stereotype.Component;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/11 15:57
 */
@Component
public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id) {
        //可以从数据库内查出数据返回,这里就简单写了
        User user1 = new User(1,"迪丽热巴",18);
        //User user2 = new User(2,"古力娜扎",19);
        return user1;
    }
}
