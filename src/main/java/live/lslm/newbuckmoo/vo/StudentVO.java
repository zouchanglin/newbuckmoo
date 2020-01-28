package live.lslm.newbuckmoo.vo;

import lombok.Data;

/**
 * 学生信息视图对象
 */
@Data
public class StudentVO {
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
    private Integer auditStatus;

    /**
     * 审核状态String
     */
    private String auditStatusStr;

    /**
     * 信息更新时间
     */
    private Long updateTime;
}
