package live.lslm.newbuckmoo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 审核时的审核意见表
 */
@Data
@Entity
public class AuditMark implements Serializable {
    private static final long serialVersionUID = 8667030534292265462L;
    @Id
    private String openId;

    private String studentMark;

    private String clubMark;

    private String companyMark;

    private Long auditStuTime;

    private Long auditClubTime;

    private Long auditCompanyTime;

    private Integer auditStuCount;

    private Integer auditClubCount;

    private Integer auditCompanyCount;
}