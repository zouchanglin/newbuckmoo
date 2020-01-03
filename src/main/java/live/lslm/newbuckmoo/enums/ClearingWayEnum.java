package live.lslm.newbuckmoo.enums;

import lombok.Getter;

/**
 * 兼职结算方式
 */
@Getter
public enum  ClearingWayEnum implements CodeEnum {
    DAY_CLEARING(0, "日结"),
    WEEK_CLEARING(1, "周结"),
    MONTH_CLEARING(2, "月结"),
    FINISH_CLEARING(3, "完工结算")
    ;

    private Integer code;
    private String message;

    ClearingWayEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}