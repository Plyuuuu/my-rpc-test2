package github.veikkoroc.test.remote.entry;

import lombok.*;

import java.io.Serializable;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/6 17:05
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 202009060859L;
    private String interfaceName; //接口名
    private String methodName;  //方法名
    private Object[] params;      // 参数
}
