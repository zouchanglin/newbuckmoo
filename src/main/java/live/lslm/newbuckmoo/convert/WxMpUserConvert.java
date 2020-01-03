package live.lslm.newbuckmoo.convert;

import live.lslm.newbuckmoo.entity.UserBasicInfo;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

public class WxMpUserConvert {
    public static UserBasicInfo mpUserConvertToUserBasicInfo(WxMpUser wxMpUser){
        UserBasicInfo userInfo = new UserBasicInfo();
        userInfo.setOpenId(wxMpUser.getOpenId());
        userInfo.setUserName(wxMpUser.getNickname());
        userInfo.setUserIcon(wxMpUser.getHeadImgUrl());
        userInfo.setUserSex(wxMpUser.getSex());
        userInfo.setUserCity(wxMpUser.getCountry() + wxMpUser.getCity());
        return userInfo;
    }
}
