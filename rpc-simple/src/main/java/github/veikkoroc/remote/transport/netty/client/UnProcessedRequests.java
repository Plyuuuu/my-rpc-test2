package github.veikkoroc.remote.transport.netty.client;

import github.veikkoroc.remote.entry.RpcResponse;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器未处理的请求
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 17:12
 */
public class UnProcessedRequests {

    private static final Map<String, CompletableFuture<RpcResponse<Object>>> UNPROCESSED_RESPONSE_FUTURES = new ConcurrentHashMap<>();

    public void put(String requestId, CompletableFuture<RpcResponse<Object>> future) {
        UNPROCESSED_RESPONSE_FUTURES.put(requestId, future);
    }

    public void complete(RpcResponse<Object> rpcResponse) {
        //等待这个 Future 的客户端将得到该结果，并且 completableFuture.complete() 之后的调用将被忽略。
        // Map 的 remove()方法将返回 被移除键值对的value
        CompletableFuture<RpcResponse<Object>> future = UNPROCESSED_RESPONSE_FUTURES.remove(rpcResponse.getRequestId());
        if (null != future) {
            future.complete(rpcResponse);
        } else {
            throw new IllegalStateException();
        }
    }

}
