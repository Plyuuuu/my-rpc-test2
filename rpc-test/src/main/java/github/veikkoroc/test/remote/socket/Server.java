package github.veikkoroc.test.remote.socket;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import github.veikkoroc.test.remote.entry.RpcRequest;
import github.veikkoroc.test.remote.entry.RpcResponse;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/8 12:19
 */
public class Server {
    /**
     * 由于 Kryo 不是线程安全的。每个线程都应该有自己的 Kryo，Input 和 Output 实例。
     * 所以，使用 ThreadLocal 存放 Kryo 对象
     */
    private static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);

        kryo.setReferences(true);//默认值为true,是否关闭注册行为,关闭之后可能存在序列化问题，一般推荐设置为 true
        kryo.setRegistrationRequired(false);//默认值为false,是否关闭循环引用，可以提高性能，但是一般不推荐设置为 true
        return kryo;
    });
    public Server() {
    }

    //需要暴露的方法
    public String addAndToString(int a,int b){
        return a+b+"";
    }
    @Test
    public void start() throws IOException {
        //创建服务端套接字
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket accept = serverSocket.accept();
        InputStream inputStream = accept.getInputStream();
        //反序列化
        Kryo kryo = kryoThreadLocal.get();
        Input input = new Input(inputStream);
        RpcRequest rpcRequest = kryo.readObject(input, RpcRequest.class);
        String string = addAndToString((Integer) rpcRequest.getParams()[0], (Integer) rpcRequest.getParams()[1]);
        System.out.println(string);
        //返回数据给客户端
        OutputStream outputStream = accept.getOutputStream();
        outputStream.write(string.getBytes());

        //关闭资源
        outputStream.close();
        inputStream.close();
        accept.close();
        serverSocket.close();

    }



}
