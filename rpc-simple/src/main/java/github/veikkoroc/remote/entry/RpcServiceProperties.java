package github.veikkoroc.remote.entry;

import lombok.*;

/**
 *
 * 1、服务版本 version
 * 2、处理一个接口有多个实现类 group
 *
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/3 21:55
 */
@NoArgsConstructor  //无参构造
@AllArgsConstructor //全参构造
@Getter             //get方法
@Setter             //set方法
@ToString           //ToString
@Builder            //使用Builder模式
public class RpcServiceProperties {
    private String version;         //服务版本
    private String group;           //服务群组
    private String serviceName;     //服务名称

    public String getRpcServicePropertiesFields(){
        return this.getServiceName()+this.getGroup()+this.getVersion();
    }
}
