package live.lslm.newbuckmoo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 学生信息认证表单
 */
@Data
public class StudentAttestationForm {

    /**
     * openid来自于Cookie，{@link live.lslm.newbuckmoo.controller.WeChatController#authorize(String)}
     */
    @NotEmpty(message = "openId缺失")
    private String openid;

    /**
     * 学生学号
     */
    @NotEmpty(message = "学号必填")
    private String number;

    /**
     * 学生姓名
     */
    @NotEmpty(message = "姓名必填")
    private String name;

    /**
     * 学生证照片
     */
    @NotEmpty(message = "学生证必上传")
    private String certificate;

    /**
     * 所在学校
     */
    @NotEmpty(message = "学校必填")
    private String school;
}