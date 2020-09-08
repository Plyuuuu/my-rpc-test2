package github.veikkoroc.test.remote.entry;

import lombok.*;

import java.io.Serializable;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/6 17:10
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RpcResponse implements Serializable {
    private String message; //响应消息
    private Object data;    //返回数据
}
