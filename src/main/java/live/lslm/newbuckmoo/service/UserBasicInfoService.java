package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.entity.UserBasicInfo;

public interface UserBasicInfoService {
    /**
     * 存入或者更新一条原始用户信息
     * @param userBasicInfo 原始信息
     * @return 保存后的openid
     */
    String updateOrCreateUserBasic(UserBasicInfo userBasicInfo);
}
