package live.lslm.newbuckmoo.enums;

import lombok.Getter;

/**
 * 各类信息审核状态
 */
@Getter
public enum AuditStatusEnum implements CodeEnum {
    AUDIT_RUNNING(0, "审核中"),
    AUDIT_SUCCESS(1, "已通过"),
    AUDIT_FAILED(2, "未通过");

    private Integer code;
    private String message;

    AuditStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}