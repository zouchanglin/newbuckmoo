package live.lslm.newbuckmoo.entity;

import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class SchoolClubInfo implements Serializable {

    private static final long serialVersionUID = -4371248931727473267L;

    @Id
    private String openId;
    /**
     * 社团名称
     */
    private String clubName;
    /**
     * 社团描述
     */
    private String clubDesc;

    /**
     * 所属学校
     */
    private String schoolName;

    /**
     * 社团负责人姓名
     */
    private String ownerName;

    /**
     * 审核状态
     */
    private Integer auditStatus = AuditStatusEnum.AUDIT_RUNNING.getCode();

    /**
     * 申请时间
     */
    private Long updateTime = System.currentTimeMillis();
}