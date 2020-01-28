package live.lslm.newbuckmoo.vo;

import lombok.Data;

@Data
public class UserBasicInfoVO {
    /**
     * 微信的openId
     */
    private String openId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userIcon;

    /**
     * 用户地址
     */
    private String userCity;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 用户积分
     */
    private Integer userGrade;

    /**
     * 用户性别
     */
    private Integer userSex;

    /**
     * 用户性别String
     */
    private String userSexStr;
}
