package github.veikkoroc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/4 10:25
 */
@AllArgsConstructor
@ToString
@Getter
public enum RpcResponseCode {
    SUCCESS(200,"远程调用成功"),
    FAIL(500,"远程调用失败");
    private final int code;
    private final String message;
}
