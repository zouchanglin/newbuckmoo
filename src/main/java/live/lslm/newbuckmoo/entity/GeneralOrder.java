package live.lslm.newbuckmoo.entity;

import live.lslm.newbuckmoo.enums.OrderTypeEnum;
import live.lslm.newbuckmoo.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class GeneralOrder {
    @Id
    private String orderId;

    /**
     * 订单的名称
     */
    private String orderName;

    /**
     * 订单类型
     */
    private Integer orderType = OrderTypeEnum.OTHER_BUY_GRADE.getCode();

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 支付者openId
     */
    private String orderOpenId;

    /**
     * 支付状态
     */
    private Integer orderPayStatus = PayStatusEnum.WILL_PAY.getCode();
}