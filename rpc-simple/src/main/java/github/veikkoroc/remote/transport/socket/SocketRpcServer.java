package github.veikkoroc.remote.transport.socket;

import github.veikkoroc.config.CustomShutdownHook;
import github.veikkoroc.provider.ServiceProvider;
import github.veikkoroc.remote.entry.RpcServiceProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;


/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/4 10:43
 */
@Slf4j
public class SocketRpcServer {

    //线程池
    private final ExecutorService threadPool;
    //服务提供者
    private final ServiceProvider serviceProvider;

    public SocketRpcServer(ExecutorService threadPool, ServiceProvider serviceProvider) {
        this.threadPool = threadPool;
        this.serviceProvider = serviceProvider;
    }
    public void registerService(Object service) {
        serviceProvider.publishService(service);
    }

    public void registerService(Object service, RpcServiceProperties rpcServiceProperties) {
        serviceProvider.publishService(service, rpcServiceProperties);
    }
    public void start() {
        try (ServerSocket server = new ServerSocket())
        {
            String host = InetAddress.getLocalHost().getHostAddress();
            server.bind(new InetSocketAddress(host, 9998));
            CustomShutdownHook.getCustomShutdownHook().clearAll();
            Socket socket;
            while ((socket = server.accept()) != null) {
                log.info("client connected [{}]", socket.getInetAddress());
                threadPool.execute(new SocketRpcRequestHandlerRunnable(socket));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            log.error("occur IOException:", e);
        }
    }




}
