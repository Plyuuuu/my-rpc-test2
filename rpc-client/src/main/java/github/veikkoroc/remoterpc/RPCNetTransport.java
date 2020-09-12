package github.veikkoroc.remoterpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 19:51
 */
public class RPCNetTransport {
    String host;
    int port;

    public RPCNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private Socket newSocket(){
        System.out.println("创建一个新的Socket连接");
        Socket socket;
        try {
            socket = new Socket(host,port);
        } catch (IOException e) {
            throw new RuntimeException("建立连接失败");
        }
        return socket;
    }
    public Object sendRequest(RpcRequest rpcRequest){
        Socket socket = null;
        try {
            //写出数据
            socket = newSocket();
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(rpcRequest);
            outputStream.flush();

            //写入数据
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object o = objectInputStream.readObject();
            objectInputStream.close();
            outputStream.close();
            return o;
        } catch (Exception e) {
            throw new RuntimeException("发送数据异常:"+e.getMessage());
        }finally {
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
