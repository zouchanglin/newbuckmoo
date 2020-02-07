package live.lslm.newbuckmoo.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class StudentResumeForm implements BasicForm {
    /**
     * openid来自于Cookie，{@link live.lslm.newbuckmoo.controller.WeChatController#authorize(String)}
     */
    @NotEmpty(message = "openId丢失")
    private String openId;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名必填")
    private String resumeName;

    /**
     * 性别
     */
    @NotNull(message = "性别必填")
    private Integer resumeSex;

    /**
     * 年龄
     */
    @NotNull(message = "年龄必填")
    @Max(message = "最大120", value = 120)
    private Integer resumeAge;

    /**
     * 学历
     */
    @NotNull(message = "学历必填")
    private Integer resumeEducation;

    /**
     * 校园经历
     */
    @NotEmpty(message = "校园经历必填(没有请填:无)")
    private String resumeHistory;

    /**
     * 居住地
     */
    @NotEmpty(message = "居住地必填")
    private String resumeAddress;

    /**
     * 期望职位：收银员、导购
     */
    @NotEmpty(message = "期望职位必填(没有请填:无)")
    private String resumeWork;

    /**
     * 职位类型
     */
    @NotNull(message = "职位类型必填")
    private Integer resumeWorkCategory;

    /**
     * 期望薪资
     */
    @NotEmpty(message = "期望薪资必填(没有请填:无)")
    private String resumeHopeMoney;

    /**
     * 自我描述
     */
    @NotEmpty(message = "自我描述必填")
    private String resumeAboutMyself;

    /**
     * 语言能力如：
     * 2019.02获得 CET4
     */
    @NotEmpty(message = "语言能力必填(没有请填:无)")
    private String resumeLanguage;

    /**
     * 获得证书如：
     * 2019.02 CET4证书
     * 2019.11 五星级志愿者证书
     */
    @NotEmpty(message = "获得证书必填(没有请填:无)")
    private String resumeCredential;
}