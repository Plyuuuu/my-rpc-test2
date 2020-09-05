package github.veikkoroc.remote.entry;


import lombok.Setter;
import github.veikkoroc.enumeration.RpcResponseCode;

import java.io.Serializable;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/3 21:15
 */
@Setter
public class RpcResponse<T> implements Serializable {
    private static final long serialVersionUID = 202009032227L;
    private String requestId;       //响应哪个请求的id
    private Integer code;           //响应码
    private String message;         //响应的消息
    private T data;                 //响应的数据

    public static <T> RpcResponse<T> success(T data,String requestId){
        RpcResponse<T> response = new RpcResponse<T>();         //创建响应实体
        response.setCode(RpcResponseCode.SUCCESS.getCode());    //设置响应码
        response.setMessage(RpcResponseCode.SUCCESS.getMessage());//设置响应消息
        response.setRequestId(requestId);                       //设置响应哪个请求的Id
        if (null!=data){                                        //设置响应的数据
            response.setData(data);
        }

        return response;
    }
    public static <T> RpcResponse<T> fail(RpcResponseCode rpcResponseCode) {
        RpcResponse<T> response = new RpcResponse<T>();
        response.setCode(rpcResponseCode.getCode());
        response.setMessage(rpcResponseCode.getMessage());
        return response;
    }


}
