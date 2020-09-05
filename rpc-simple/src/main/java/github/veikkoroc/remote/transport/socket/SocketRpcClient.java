package github.veikkoroc.remote.transport.socket;

import github.veikkoroc.extension.ExtensionLoader;
import github.veikkoroc.registry.ServiceDiscovery;
import github.veikkoroc.remote.entry.RpcRequest;
import github.veikkoroc.remote.entry.RpcServiceProperties;
import github.veikkoroc.remote.transport.ClientTransport;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/4 10:42
 */
public class SocketRpcClient implements ClientTransport {
    //注销注册的服务
    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient(){
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("zk");
    }


    @Override
    public Object sendRpcRequest(RpcRequest rpcRequest) {
        //构建Rpc服务名通过RpcRequest
        String rpcServerName = RpcServiceProperties.builder().serviceName(rpcRequest.getInterfaceName()).group(rpcRequest.getGroup())
                .version(rpcRequest.getVersion()).build().getRpcServicePropertiesFields();

        return null;
    }
}
