package github.veikkoroc.entry;

import lombok.*;

/**
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/11 15:48
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class User {
    //用户编号
    private Integer userId;
    //用户姓名
    private String username;
    //用户年龄
    private Integer age;
}
