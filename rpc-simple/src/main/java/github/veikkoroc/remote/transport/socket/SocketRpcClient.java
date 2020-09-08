package github.veikkoroc.remote.transport.socket;

import github.veikkoroc.extension.ExtensionLoader;
import github.veikkoroc.registry.ServiceDiscovery;
import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.remote.entry.RpcServiceProperties;
import github.veikkoroc.remote.transport.ClientTransport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/4 10:42
 */
public class SocketRpcClient implements ClientTransport {
    //发现服务
    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient(){
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("zk");
    }


    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        //构建Rpc服务名通过RpcRequest.接口名+group+版本
        String rpcServerName = RpcServiceProperties.builder().serviceName(rpcRequest.getInterfaceName()).group(rpcRequest.getGroup())
                .version(rpcRequest.getVersion()).build().getRpcServicePropertiesFields();

        //从注册中心获取该服务  IP:端口号 找到服务提供者所在的位置
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcServerName);

        try(Socket socket = new Socket()){
            //连接到服务
            socket.connect(inetSocketAddress);
            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            //发送数据到服务器
            objectOutputStream.writeObject(rpcRequest);
            //接收服务器返回的数据
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("调用服务失败:", e);
        }

    }
}
