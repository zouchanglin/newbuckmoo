package live.lslm.newbuckmoo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 微信用户基本信息
 */
@Entity
@DynamicUpdate
@Data
public class UserBasicInfo {
    /**
     * 微信的openId
     */
    @Id
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
}