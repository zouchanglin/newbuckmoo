package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.catchs.VerifyKeyCatch;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.enums.UserSexEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.BindPhoneForm;
import live.lslm.newbuckmoo.repository.UserBasicInfoRepository;
import live.lslm.newbuckmoo.service.UserBasicInfoService;
import live.lslm.newbuckmoo.utils.EnumUtil;
import live.lslm.newbuckmoo.vo.UserBasicInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserBasicInfoServiceImpl implements UserBasicInfoService {
    @Autowired
    private UserBasicInfoRepository userBasicInfoRepository;

    @Override
    public UserBasicInfoVO getUserBasicVOByOpenId(String openId) {
        Optional<UserBasicInfo> findResult = userBasicInfoRepository.findById(openId);
        if(findResult.isPresent()){
            UserBasicInfo userBasicInfo = findResult.get();
            UserBasicInfoVO userBasicInfoVO = new UserBasicInfoVO();
            BeanUtils.copyProperties(userBasicInfo, userBasicInfoVO);
            userBasicInfoVO.setUserSexStr(EnumUtil.getByCode(userBasicInfo.getUserSex(), UserSexEnum.class).getMessage());
            return userBasicInfoVO;
        }
        return null;
    }

    @Override
    public void bindPhoneForUser(BindPhoneForm bindPhoneForm) {
        Optional<UserBasicInfo> findResult = userBasicInfoRepository.findById(bindPhoneForm.getOpenId());
        String phone = bindPhoneForm.getPhone();
        String verifyKey = bindPhoneForm.getVerifyKey();
        if(findResult.isPresent()){
            UserBasicInfo userBasicInfo = findResult.get();
            String findVerifyKey = VerifyKeyCatch.getVerifyKey(phone);
            if(findVerifyKey != null && findVerifyKey.equals(verifyKey)){
                userBasicInfo.setUserPhone(phone);
                UserBasicInfo savedResult = userBasicInfoRepository.save(userBasicInfo);
                log.info("[UserBasicInfoServiceImpl] savedResult={}", savedResult);
                boolean rmVerifyKey = VerifyKeyCatch.rmVerifyKey(phone);
                log.info("[UserBasicInfoServiceImpl] rmVerifyKey={}", rmVerifyKey);
            }else {
                throw new BuckmooException(ResultEnum.VERIFY_KEY_ERROR);
            }
        }else{
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }

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
