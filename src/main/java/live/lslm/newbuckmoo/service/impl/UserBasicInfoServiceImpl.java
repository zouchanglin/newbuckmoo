package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.repository.UserBasicInfoRepository;
import live.lslm.newbuckmoo.service.UserBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBasicInfoServiceImpl implements UserBasicInfoService {
    @Autowired
    private UserBasicInfoRepository userBasicInfoRepository;

    @Override
    public String updateOrCreateUserBasic(UserBasicInfo userBasicInfo) {
        Optional<UserBasicInfo> basicInfoRet = userBasicInfoRepository.findById(userBasicInfo.getOpenId());
        if(basicInfoRet.isPresent()){
            UserBasicInfo findBasicInfo = basicInfoRet.get();
            userBasicInfo.setUserPhone(findBasicInfo.getUserPhone());
            userBasicInfo.setUserGrade(findBasicInfo.getUserGrade());
        }
        return userBasicInfoRepository.save(userBasicInfo).getOpenId();
    }

    @Override
    public UserBasicInfo getUserBasicInfoByOpenid(String openId) {
        return userBasicInfoRepository.findById(openId).orElse(null);
    }

    @Override
    public void deleteOneUserBasicInfoById(String openid) {
        userBasicInfoRepository.deleteById(openid);
    }
}
