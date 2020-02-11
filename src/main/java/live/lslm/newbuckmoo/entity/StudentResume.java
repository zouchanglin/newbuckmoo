package live.lslm.newbuckmoo.entity;

import live.lslm.newbuckmoo.enums.UserEducationEnum;
import live.lslm.newbuckmoo.enums.UserSexEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 学生简历
 */
@Entity
@DynamicUpdate
@Data
public class StudentResume implements Serializable {
    private static final long serialVersionUID = 4293419265025095898L;
    @Id
    private String openId;

    /**
     * 姓名
     */
    private String resumeName;

    /**
     * 性别
     */
    private Integer resumeSex = UserSexEnum.MAN.getCode();

    /**
     * 年龄
     */
    private Integer resumeAge;

    /**
     * 学历
     */
    private Integer resumeEducation = UserEducationEnum.REGULAR_COLLEGE.getCode();

    /**
     * 校园经历
     */
    private String resumeHistory;

    /**
     * 居住地
     */
    private String resumeAddress;

    /**
     * 期望职位：收银员、导购
     */
    private String resumeWork;

    /**
     * 职位类型
     */
    private Integer resumeWorkCategory;

    /**
     * 期望薪资
     */
    private String resumeHopeMoney;

    /**
     * 自我描述
     */
    private String resumeAboutMyself;

    /**
     * 语言能力如：
     * 2019.02获得 CET4
     */
    private String resumeLanguage;

    /**
     * 获得证书如：
     * 2019.02 CET4证书
     * 2019.11 五星级志愿者证书
     */
    private String resumeCredential;

    /**
     * 信息更新时间
     */
    private Long updateTime;
}