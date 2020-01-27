package live.lslm.newbuckmoo.enums;

import lombok.Getter;

@Getter
public enum ReadStatusEnum implements CodeEnum {
    NOT_READ(0, "未读"),
    READ(1, "已读");

    private Integer code;
    private String message;

    ReadStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
