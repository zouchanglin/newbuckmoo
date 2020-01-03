package live.lslm.newbuckmoo.entity;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * 学生信息
 */
@Data
@Entity
@DynamicUpdate
public class StudentInfo {
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
}