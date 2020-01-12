package live.lslm.newbuckmoo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import live.lslm.newbuckmoo.entity.CategoryInfo;
import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.utils.EnumUtil;
import lombok.Data;
import live.lslm.newbuckmoo.enums.UserEducationEnum;
import live.lslm.newbuckmoo.enums.UserSexEnum;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 学生简历
 */
@Data
public class StudentResumeDTO {
    private String openId;

    /**
     * 姓名
     */
    private String resumeName;

    /**
     * 性别
     */
    private Integer resumeSex;

    /**
     * 年龄
     */
    private Integer resumeAge;

    /**
     * 学历
     */
    private Integer resumeEducation;

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

    /**
     * 学生信息
     */
    private StudentInfo studentInfo;

    /**
     * 基础信息
     */
    private UserBasicInfo userBasicInfo;

    /**
     * 兼职分类信息
     */
    private CategoryInfo categoryInfo;


    /**
     * 获得性别
     */
    @JsonIgnore
    public UserSexEnum getSex(){
        UserSexEnum sexEnum = EnumUtil.getByCode(this.resumeSex, UserSexEnum.class);
        if(sexEnum == null) throw new BuckmooException(ResultEnum.PARAM_ERROR);
        return sexEnum;
    }

    /**
     * 获得信息更新时间
     */
    @JsonIgnore
    public String getUpdateTime() {
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date(updateTime));
    }

    /**
     * 获得学历
     */
    @JsonIgnore
    public UserEducationEnum getEducation(){
        UserEducationEnum educationEnum = EnumUtil.getByCode(this.resumeEducation, UserEducationEnum.class);
        if(educationEnum == null) throw new BuckmooException(ResultEnum.PARAM_ERROR);
        return educationEnum;
    }
}