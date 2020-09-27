package github.veikkoroc.entry;

import lombok.*;

/**
 *
 * @author Veikko Roc
 * @version 1.0
 * @date 2020/9/27 11:58
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    /**
     * 订单编号
     */
    private int id;

    /**
     * 订单主人名字
     */
    private String userName;
    /**
     * 订单时间
     */
    private String time;
    /**
     * 产品名
     */
    private String productName;
}
