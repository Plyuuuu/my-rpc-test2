package github.veikkoroc.remote.transport;

import github.veikkoroc.remote.entry.RpcRequest;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/4 10:40
 */
public interface ClientTransport {
    /**
     *
     * @param rpcRequest
     * @return  result from server
     */
   public Object sendRpcRequest(RpcRequest rpcRequest);
}
