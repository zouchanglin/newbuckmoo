package live.lslm.newbuckmoo.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {
    SUCCESS(0, "成功"),
    PARAM_ERROR(1, "参数错误"),
    AUDIT_STATUS_ERROR(2, "审核状态错误"),
    PASS_AUDIT(3, "审核通过"),
    NOT_PASS_AUDIT(4, "审核未通过"),
    SEND_MESSAGE_ERROR(5, "短信发送失败"),
    VERIFY_KEY_ERROR(6, "验证码不正确");


    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
