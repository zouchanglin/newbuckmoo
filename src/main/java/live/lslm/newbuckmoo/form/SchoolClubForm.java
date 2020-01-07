package live.lslm.newbuckmoo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SchoolClubForm implements BasicForm{

    /**
     * openid来自于Cookie，{@link live.lslm.newbuckmoo.controller.WeChatController#authorize(String)}
     */
    @NotEmpty(message = "openid丢失")
    private String openId;

    /**
     * 社团名称
     */
    @NotEmpty(message = "社团名称必填")
    private String clubName;

    /**
     * 社团描述
     */
    @NotEmpty(message = "社团描述必填")
    private String clubDesc;

    /**
     * 所属学校
     */
    @NotEmpty(message = "学校名称必填")
    private String schoolName;

    /**
     * 社团负责人姓名
     */
    @NotEmpty(message = "负责人姓名必填")
    private String ownerName;

    /**
     * 邀请码
     */
    @NotEmpty(message = "邀请码必填")
    private String clubCode;
}