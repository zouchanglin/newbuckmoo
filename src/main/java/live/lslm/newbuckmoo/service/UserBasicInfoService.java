package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.entity.UserBasicInfo;

public interface UserBasicInfoService {
    /**
     * 存入或者更新一条原始用户信息
     * @param userBasicInfo 原始信息
     * @return 保存后的openid
     */
    String updateOrCreateUserBasic(UserBasicInfo userBasicInfo);

    /**
     * 根据openid拿到一条用户的基本信息
     * @param openId openid
     * @return 用户基本信息
     */
    UserBasicInfo getUserBasicInfoByOpenid(String openId);

    /**
     * 根据openid删除一条基本用户信息
     * @param openId openid
     */
    void deleteOneUserBasicInfoById(String openId);
}
