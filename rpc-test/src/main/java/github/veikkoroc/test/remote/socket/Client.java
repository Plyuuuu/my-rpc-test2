package github.veikkoroc.test.remote.socket;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import github.veikkoroc.test.remote.entry.RpcRequest;

import github.veikkoroc.test.remote.entry.RpcResponse;
import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/8 12:19
 */
public class Client {
    /**
     * 由于 Kryo 不是线程安全的。每个线程都应该有自己的 Kryo，Input 和 Output 实例。
     * 所以，使用 ThreadLocal 存放 Kryo 对象
     */
    private static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        kryo.register(Object.class);
        kryo.setReferences(true);//默认值为true,是否关闭注册行为,关闭之后可能存在序列化问题，一般推荐设置为 true
        kryo.setRegistrationRequired(false);//默认值为false,是否关闭循环引用，可以提高性能，但是一般不推荐设置为 true
        return kryo;
    });
    @Test
    public void start() throws IOException{

        //先把要传输的对象创建且序列化
        //创建对象
        Object[] params = new Object[]{1,2};
        //System.out.println(Arrays.toString(objects));
        RpcRequest rpcRequest = new RpcRequest("interfaceName","methodName",params);
        //序列化
        //获取kryo
        Kryo kryo = kryoThreadLocal.get();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);
        //Object --> bytes
        kryo.writeObject(output,rpcRequest);//对象写入了output
        kryoThreadLocal.remove();
        //通过Socket网络传输
        Socket socket = new Socket(InetAddress.getLocalHost(),8888);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(output.toBytes());

        //获得服务端返回的数据
        Kryo kryo1 = kryoThreadLocal.get();
        InputStream inputStream = socket.getInputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(inputStream.readAllBytes());
        Input input = new Input(byteArrayInputStream);
        Object o = kryo1.readObject(input, Object.class);
        System.out.println(o.toString());
        kryoThreadLocal.remove();
        //关闭资源
        inputStream.close();
        outputStream.close();
        socket.close();
        output.close();
        byteArrayOutputStream.close();

    }
}
