package live.lslm.newbuckmoo.enums;

import lombok.Getter;

@Getter
public enum OrderTypeEnum implements CodeEnum{
    BUY_365YEAR_MEMBER(0, "365/年费会员"),
    ONE_ACTIVITY_TOP_WEAK(1, "单条活动置顶一周"),
    ONE_POSITION_TOP_WEAK(2, "单条兼职置顶一周"),
    ONE_ACTIVITY_TOP_MONTH(3, "单条活动置顶一周"),
    ONE_POSITION_TOP_MONTH(4, "单条兼职置顶一周");

    private Integer code;
    private String message;

    OrderTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
