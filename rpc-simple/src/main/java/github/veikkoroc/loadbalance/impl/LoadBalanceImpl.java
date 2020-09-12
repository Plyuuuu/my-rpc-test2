package github.veikkoroc.loadbalance.impl;

import github.veikkoroc.loadbalance.LoadBalance;
import org.junit.Test;

import java.util.List;
import java.util.Random;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 15:33
 */
public class LoadBalanceImpl implements LoadBalance {

    @Override
    public String selectServiceAddress(List<String> serviceAddresses) {
        if (serviceAddresses == null || serviceAddresses.size()==0){//服务器列表为null或者为空，返回null
            return null;
        }
        if (serviceAddresses.size() == 1){//只有一台服务器 返回该服务器
            return serviceAddresses.get(0);
        }
        //如果有多台服务器的话，随机找一个

        return doSelect(serviceAddresses);
    }
    private String doSelect(List<String> serviceAddresses){
        Random random = new Random();
        int i = random.nextInt(serviceAddresses.size());
        return serviceAddresses.get(i);

    }

    /*
    @Test
    public void testRandom(){
        Random random = new Random();
        int i = random.nextInt(2);//[0-1]
        System.out.println(i);
    }*/

}
