package github.veikkoroc.remoterpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/10 18:03
 */
public class RpcServerProxy {
    //缓冲线程池，没有核心线程控制
    ExecutorService executorService = Executors.newCachedThreadPool();

    //发布服务
    public void publish(Object service,int port){
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            while (true){//持续监听
                Socket accept = serverSocket.accept();//接收请求
                executorService.execute(new ProcessorHandler(accept,service));

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
