package live.lslm.newbuckmoo.aspect;

import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.BasicForm;
import live.lslm.newbuckmoo.service.UserBasicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Aspect
@Component
public class AttestationAspect {
    @Autowired
    private UserBasicInfoService userBasicInfoService;

    @Before("execution(public live.lslm.newbuckmoo.vo.ResultVO live.lslm.newbuckmoo.controller.AttestationController.*(..))")
    public void toBindPhone(JoinPoint joinPoint){
        BasicForm basicForm = (BasicForm)joinPoint.getArgs()[0];
        String openId = basicForm.getOpenId();
        log.info("[切面拦截未绑定用户注册其他高级用户] OpenId={}", openId);
        UserBasicInfo userBasicInfoByOpenid = userBasicInfoService.getUserBasicInfoByOpenid(openId);
        if(userBasicInfoByOpenid == null) {
            //参数OpenId错误
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
        if(StringUtils.isEmpty(userBasicInfoByOpenid.getUserPhone())){
            //未绑定手机
            throw new BuckmooException(ResultEnum.NOT_BIND_PHONE);
        }
    }
}