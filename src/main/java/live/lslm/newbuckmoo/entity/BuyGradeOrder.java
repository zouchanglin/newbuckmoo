package live.lslm.newbuckmoo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 购买积分订单
 */
@Data
@Entity
public class BuyGradeOrder {
    @Id
    private String orderId;

    private Integer gradeComboId;

    private String buyerOpenId;

    private String orderOther;
}
