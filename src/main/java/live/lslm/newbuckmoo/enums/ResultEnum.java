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
    VERIFY_KEY_ERROR(6, "验证码不正确"),
    OPENID_CLUB_ERROR(7, "社团无对应OpenId用户"),
    OPENID_STUDENT_ERROR(8, "学生无对应OpenId用户"),
    NOT_BIND_PHONE(9, "请先绑定手机"),
    COOKIE_NOT_EXIST(10, "Cookie(openId)不存在"),
    AUDITING_NOT_ALLOWED(11, "审核中不允许更改信息"),
    PERMISSION_ERROR(12, "权限拒绝"),
    REPETITION_ERROR(13, "重复操作"),
    PERFECT_RESUME(14, "请先完善简历");

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
