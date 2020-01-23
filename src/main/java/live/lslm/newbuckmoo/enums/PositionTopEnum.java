package live.lslm.newbuckmoo.enums;

import lombok.Getter;

@Getter
public enum PositionTopEnum implements CodeEnum{
    IS_TOP(0, "置顶"),
    NTO_TOP(1, "不置顶");

    private Integer code;
    private String message;

    PositionTopEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
