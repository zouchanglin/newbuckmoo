package live.lslm.newbuckmoo.enums;

import lombok.Getter;

@Getter
public enum WorkExperienceEnum implements CodeEnum{
    IN_SCHOOL(1, "在校生"),
    ZERO(2, "应届生"),
    ONE_TWO(3, "1-2年"),
    THREE_FIVE(4, "3-5年"),
    SEX_TEN(5, "6-10年");

    private Integer code;
    private String message;

    WorkExperienceEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
