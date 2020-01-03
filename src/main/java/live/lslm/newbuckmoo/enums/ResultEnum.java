package live.lslm.newbuckmoo.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数错误");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
