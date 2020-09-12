package github.veikkoroc.remote.entry;

import github.veikkoroc.enumeration.RpcResponseCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 验证RpcRequest和RpcRequest
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/12 21:40
 */
@Slf4j
public class RpcMessageChecker {
    private static final String INTERFACE_NAME = "interfaceName";

    private RpcMessageChecker() {
    }

    public static void check(RpcResponse<Object> rpcResponse, RpcRequest rpcRequest) {
        if (rpcResponse == null) {
            throw new RuntimeException("服务器调用失败："+INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RuntimeException("返回结果错误！请求和返回的相应不匹配："+INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());

        }

        if (rpcResponse.getCode() == null || !rpcResponse.getCode().equals(RpcResponseCode.SUCCESS.getCode()))
            throw new RuntimeException("服务器调用失败："+INTERFACE_NAME + ":" + rpcRequest.getInterfaceName());{

        }
    }
}
