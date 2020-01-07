package live.lslm.newbuckmoo.form;

import lombok.Data;

/**
 * 绑定手机号的表单
 */
@Data
public class BindPhoneForm implements BasicForm{
    /**
     * openid
     */
    private String openId;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String verifyKey;
}
