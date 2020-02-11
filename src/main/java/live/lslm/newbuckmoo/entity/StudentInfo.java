package live.lslm.newbuckmoo.entity;


import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * 学生信息
 */
@Data
@Entity
@DynamicUpdate
public class StudentInfo implements Serializable {
    private static final long serialVersionUID = -4289303194271562116L;
    @Id
    private String openId;

    /**
     * 学生学号
     */
    private String studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生证照片
     */
    private String studentCertificate;

    /**
     * 所在学校
     */
    private String studentSchool;

    /**
     * 简历Id
     */
    private String studentResume;

    /**
     * 审核状态
     */
    private Integer auditStatus = AuditStatusEnum.AUDIT_RUNNING.getCode();

    /**
     * 信息更新时间
     */
    private Long updateTime = System.currentTimeMillis();
}