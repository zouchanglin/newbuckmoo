package live.lslm.newbuckmoo.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum implements CodeEnum{
    WILL_PAY(0, "未支付"),
    FINISH_PAY(1, "已支付"),
    REFUND_PAY(2, "已退款");

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
