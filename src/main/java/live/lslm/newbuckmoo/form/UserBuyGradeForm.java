package live.lslm.newbuckmoo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 用户购买积分的表单
 */
@Data
public class UserBuyGradeForm {
    /* 用户的openId */
    @NotEmpty(message = "openId丢失")
    private String openId;

    /* 积分套餐ID */
    @NotNull(message = "积分套餐必填")
    private Integer gradeComboId;

    /* 支付完毕返回地址 */
    @NotEmpty(message = "返回地址必填")
    private String returnUrl;
}