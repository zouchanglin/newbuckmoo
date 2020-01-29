package live.lslm.newbuckmoo.vo;

import lombok.Data;

@Data
public class StudentResumeVO {
    /**
     * 姓名
     */
    private String resumeName;

    /**
     * 性别
     */
    private Integer resumeSex;

    /**
     * 性别
     */
    private String resumeSexStr;

    /**
     * 年龄
     */
    private Integer resumeAge;

    /**
     * 学历
     */
    private Integer resumeEducation;

    /**
     * 学历
     */
    private String resumeEducationStr;

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
     * 期望职位类型
     */
    private Integer resumeWorkCategory;

    /**
     * 期望职位类型
     */
    private String resumeWorkCategoryStr;

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
     * 信息最近更新时间
     */
    private Long updateTime;

    /**
     * 信息最近更新时间
     */
    private String updateTimeStr;
}