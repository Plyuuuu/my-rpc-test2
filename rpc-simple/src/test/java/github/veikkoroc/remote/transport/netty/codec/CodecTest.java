package github.veikkoroc.remote.transport.netty.codec;

import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.serialize.KryoSerializer;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/15 11:27
 */
public class CodecTest {
    public static void main(String[] args) {
        //创建需要传输的对象
        RpcRequest rpcRequest = RpcRequest.builder().requestId("123").interfaceName("github.veikkoroc.service.UserService").group("1").version("1.0").build();
        //创建编码解码器用到的序列化工具
        KryoSerializer kryoSerializer = new KryoSerializer();
        //创建编码解码器
        KryoEncoder kryoEncoder = new KryoEncoder(kryoSerializer,RpcRequest.class);


    }
}
