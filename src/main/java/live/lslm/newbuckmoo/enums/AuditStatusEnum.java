package live.lslm.newbuckmoo.enums;

import lombok.Getter;

/**
 * 各类信息审核状态
 */
@Getter
public enum AuditStatusEnum implements CodeEnum {
    AUDIT_FUTURE(0, "未审核"),
    AUDIT_RUNNING(1, "审核中"),
    AUDIT_SUCCESS(2, "审核成功"),
    AUDIT_FAILED(3, "审核未通过");

    private Integer code;
    private String message;

    AuditStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}