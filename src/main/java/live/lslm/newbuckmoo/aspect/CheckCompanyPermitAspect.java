package live.lslm.newbuckmoo.aspect;

import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.PositionInfoForm;
import live.lslm.newbuckmoo.repository.CompanyInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 企业操作权限拦截器
 */
@Slf4j
@Aspect
@Component
public class CheckCompanyPermitAspect {
    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Before("execution(public * live.lslm.newbuckmoo.controller.company.PositionController.*(..))"+"" +
            "&& !execution(public * live.lslm.newbuckmoo.controller.company.PositionController.getCategoryList())")
    public void checkCompanyPermit(JoinPoint joinPoint){
        PositionInfoForm form = (PositionInfoForm)joinPoint.getArgs()[0];
        String openId = form.getOpenId();
        String companyId = form.getPositionCompanyId();
        log.info("[拦截到企业操作]...openId={}, companyId={}", openId, companyId);

        Optional<CompanyInfo> companyInfoOpt = companyInfoRepository.findById(openId);
        //根据openId找不到企业信息
        if(!companyInfoOpt.isPresent()) throw new BuckmooException(ResultEnum.PERMISSION_ERROR);

        CompanyInfo companyInfo = companyInfoOpt.get();

        //根据openId查出的企业信息不正确
        if(!companyId.equals(companyInfo.getCompanyId())) throw new BuckmooException(ResultEnum.PERMISSION_ERROR);

        //审核状态不具备操作能力
        if(!AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(companyInfo.getAuditStatus())){
            throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
        }
        //检验openId是否和表单中的openId一致
        if(!openId.equals(companyInfo.getOpenId())) throw new BuckmooException(ResultEnum.PERMISSION_ERROR);
    }
}