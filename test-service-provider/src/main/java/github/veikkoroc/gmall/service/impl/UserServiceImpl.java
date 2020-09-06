package github.veikkoroc.gmall.service.impl;

import github.veikkoroc.gmall.bean.UserAddress;
import github.veikkoroc.gmall.service.UserService;

import java.util.Arrays;
import java.util.List;

/**
 * 1、将服务提供者注册到注册中心（暴露服务）
 *  1）、导入 dubbo 依赖（2.6.2）\操作zookeeper的客户端（curator)
 *  2）、配置服务提供者
 * 2、服务消费者去注册中心订阅服务提供者的服务地址
 *
 *
 *
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/6 11:44
 */
public class UserServiceImpl implements UserService {
    @Override
    public List<UserAddress> getUserAddressList(String userId) {
        UserAddress address = new UserAddress(1,"南昌市江西理工大学","1","江理吴彦祖","177777777","Y");
        UserAddress address1 = new UserAddress(2,"上饶市铅山县","1","铅山婆婆子","177777777","N");
        return Arrays.asList(address,address1);
    }
}
