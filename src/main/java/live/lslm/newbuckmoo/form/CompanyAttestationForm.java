package live.lslm.newbuckmoo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 企业注册信息的表单
 */
@Data
public class CompanyAttestationForm implements BasicForm{

    /**
     * openid来自于Cookie，{@link live.lslm.newbuckmoo.controller.WeChatController#authorize(String)}
     */
    @NotEmpty(message = "openId丢失")
    private String openId;

    /**
     * 公司名称
     */
    @NotEmpty(message = "公司名称必填")
    private String name;

    /**
     * 工商注册号
     */
    @NotEmpty(message = "工商注册号必填")
    private String number;

    /**
     * 法定代表人姓名
     */
    @NotEmpty(message = "法人姓名必填")
    private String owner;

    /**
     * 营业执照路径
     */
    @NotEmpty(message = "营业执照必填")
    private String certificate;

    /**
     * 经营范围描述
     */
    @NotEmpty(message = "经营范围必填")
    private String describe;
}