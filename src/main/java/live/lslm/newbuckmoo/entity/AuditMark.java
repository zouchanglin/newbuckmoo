package live.lslm.newbuckmoo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 审核时的审核意见表
 */
@Data
@Entity
public class AuditMark {
    @Id
    private String openId;

    private String studentMark;

    private String clubMark;

    private Long auditStuTime;

    private Long auditClubTime;

    private Integer auditStuCount;

    private Integer auditClubCount;
}