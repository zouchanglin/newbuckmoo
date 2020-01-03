package live.lslm.newbuckmoo.enums;


import lombok.Getter;

/**
 * 用户性别枚举
 */
@Getter
public enum UserSexEnum implements CodeEnum{
    MAN(1, "男"),
    WOMAN(2, "女");

    private Integer code;
    private String message;

    UserSexEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
