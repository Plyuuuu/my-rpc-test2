package github.veikkoroc.remote.entry;

import github.veikkoroc.enumeration.RpcMessageType;
import lombok.*;

import java.io.Serializable;

/**
 * RPC请求实体类：
 *      通过网络传输到服务端
 *      包含了要调用的目标方法和类的名称、参数等数据
 *
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/3 21:15
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcRequest implements Serializable {
    /**
     *随便设，就设为日期时间吧
     */
    private static final long serialVersionID = 202009032124L;
    /**
     *请求编号
     */
    private String requestId;
    /**
     *传输的接口名
     */
    private String interfaceName;
    /**
     *调用的方法名
     */
    private String methodName;
    /**
     *传入的参数
     */
    private Object[] parameters;
    /**
     *传入参数的类型
     */
    private Class<?>[] paramTypes;
    /**
     *信息类型
     */
    private RpcMessageType rpcMessageType ;
    /**
     *服务版本
     */
    private String version;
    /**
     *处理一个类有多个接口
     */
    private String group;

    /**
     *
     * @return 服务属性（接口名、版本和组）
     */
    public RpcServiceProperties toRpcServiceProperties(){
        return RpcServiceProperties.builder()
                .serviceName(this.interfaceName)
                .version(this.version)
                .group(this.group).build();
    }

}
