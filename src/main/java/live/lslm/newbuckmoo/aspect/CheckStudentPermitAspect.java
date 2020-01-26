package live.lslm.newbuckmoo.aspect;

import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.RequestByPageForm;
import live.lslm.newbuckmoo.repository.CompanyInfoRepository;
import live.lslm.newbuckmoo.repository.StudentInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 学生操作权限拦截器
 */
@Slf4j
@Aspect
@Component
public class CheckStudentPermitAspect {

    @Autowired
    private StudentInfoRepository studentInfoRepository;

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Before("execution(public * live.lslm.newbuckmoo.controller.student.StuPositionController.*(..))")
    public void checkCompanyPermit(JoinPoint joinPoint){
        log.info("【学生/企业查看兼职信息】权限验证");
        RequestByPageForm pageForm = (RequestByPageForm) joinPoint.getArgs()[0];
        String openId = pageForm.getOpenId();
        Optional<StudentInfo> studentInfo = studentInfoRepository.findById(openId);
        Optional<CompanyInfo> companyInfo = companyInfoRepository.findById(openId);

        //是学生/企业并且审核通过
        if(!((studentInfo.isPresent() && AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(studentInfo.get().getAuditStatus())) ||
                (companyInfo.isPresent() && AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(companyInfo.get().getAuditStatus())))){
            throw new BuckmooException(ResultEnum.PERMISSION_ERROR);
        }
    }
}