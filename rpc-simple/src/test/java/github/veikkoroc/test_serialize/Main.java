package github.veikkoroc.test_serialize;

import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.serialize.KryoSerializer;

import java.util.Arrays;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 10:08
 */
public class Main {
    public static void main(String[] args) {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setRequestId("111");
        //序列化
        KryoSerializer kryoSerializer = new KryoSerializer();
        byte[] serialize = kryoSerializer.serialize(rpcRequest);
        System.out.println(Arrays.toString(serialize));
        //反序列化
        RpcRequest deserialize = kryoSerializer.deserialize(serialize,RpcRequest.class);
        System.out.println(deserialize);
    }
}
