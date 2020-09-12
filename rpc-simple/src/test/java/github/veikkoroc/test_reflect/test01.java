package github.veikkoroc.test_reflect;

import github.veikkoroc.remote.entry.RpcRequest;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 11:59
 */
public class test01 {
    public static void main(String[] args) {
        RpcRequest rpcRequest = new RpcRequest();

        rpcRequest.setMethodName("Hhh");
        Object o = rpcRequest;
        System.out.println(o);
        System.out.println(o.getClass());
        try {
            System.out.println(o.getClass().getMethod("getMethodName"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
