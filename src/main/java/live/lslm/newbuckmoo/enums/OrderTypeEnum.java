package live.lslm.newbuckmoo.enums;

import lombok.Getter;

@Getter
public enum OrderTypeEnum implements CodeEnum{
    STUDENT_BUY_GRADE(0, "学生用户购买积分"),
    COMPANY_BUY_GRADE(1, "企业用户购买积分"),
    CLUB_BUY_GRADE(2, "社团用户购买积分"),
    OTHER_BUY_GRADE(3, "社团用户购买积分");

    private Integer code;
    private String message;

    OrderTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}