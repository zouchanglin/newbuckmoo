package live.lslm.newbuckmoo.enums;

import lombok.Getter;

/**
 * 用户学历枚举
 */
@Getter
public enum UserEducationEnum implements CodeEnum{
    DOCTORAL(1, "博士"),
    POSTGRADUATE(2, "硕士"),
    REGULAR_COLLEGE(3, "本科"),
    ASSOCIATE_DEGREE(4, "大专");

    private Integer code;
    private String message;

    UserEducationEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}