package github.veikkoroc.test.remote.entry;

import lombok.*;

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
public class RpcRequest {
    private String interfaceName;
    private String methodName;
}
