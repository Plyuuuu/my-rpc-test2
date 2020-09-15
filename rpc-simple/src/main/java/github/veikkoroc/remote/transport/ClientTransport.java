package github.veikkoroc.remote.transport;

import github.veikkoroc.remote.entry.RpcRequest;

/**
 * 客户传输消息
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/4 10:40
 */
public interface ClientTransport {
    /**
     *
     * @param rpcRequest  请求对象
     * @return  服务器返回请求结果
     */
   public Object sendRpcRequest(RpcRequest rpcRequest);
}
