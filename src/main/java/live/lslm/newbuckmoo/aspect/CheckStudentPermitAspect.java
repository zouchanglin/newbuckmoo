package live.lslm.newbuckmoo.aspect;

import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.RequestByPageForm;
import live.lslm.newbuckmoo.form.StudentApplyPositionForm;
import live.lslm.newbuckmoo.repository.CompanyInfoRepository;
import live.lslm.newbuckmoo.repository.StudentInfoRepository;
import live.lslm.newbuckmoo.service.StudentResumeService;
import live.lslm.newbuckmoo.vo.StudentResumeVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
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

    @Autowired
    private StudentResumeService studentResumeService;

    /**
     * 学生 Or 企业可以查看兼职
     */
    @Before("execution(public * live.lslm.newbuckmoo.controller.student.StuPositionController.getPositionList(..))")
    public void checkCompanyOrStudentPermit(JoinPoint joinPoint){
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

        //如果是学生需要完善简历
        if(studentInfo.isPresent()){
            StudentResumeVO oneResumeByOpenId = studentResumeService.getOneResumeByOpenId(studentInfo.get().getOpenId());
            if(oneResumeByOpenId.equals(new StudentResumeVO())){
                throw new BuckmooException(ResultEnum.PERFECT_RESUME);
            }
        }
    }

    /**
     * 学生本人才可以查看简历
     */
    @Before("execution(public * live.lslm.newbuckmoo.controller.student.ResumeController.getMyResume(..))")
    public void checkMySelfShowResume(JoinPoint joinPoint){
        Map map = (Map) joinPoint.getArgs()[0];
        String openId = (String) map.get("openId");
        log.info("【学生查看自己简历】权限验证 openId={}", openId);
        Optional<StudentInfo> studentInfoOpt = studentInfoRepository.findById(openId);
        //非学生用户
        if(!studentInfoOpt.isPresent()) {
            throw new BuckmooException(ResultEnum.PERMISSION_ERROR);
        }
        //学生用户未审核/审核未通过
        if(!AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(studentInfoOpt.get().getAuditStatus())){
            throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
        }
    }

    /**
     * 学生申请兼职信息权限验证
     */
    @Before("execution(public * live.lslm.newbuckmoo.controller.student.StuPositionController.studentApplyPosition(..))")
    public void checkStudentPermit(JoinPoint joinPoint){
        log.info("【学生申请兼职信息】权限验证");
        StudentApplyPositionForm applyForm = (StudentApplyPositionForm) joinPoint.getArgs()[0];
        String openId = applyForm.getOpenId();
        Optional<StudentInfo> studentInfo = studentInfoRepository.findById(openId);

        //是学生并且审核通过
        if(!(studentInfo.isPresent() && (AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(studentInfo.get().getAuditStatus())))){
            throw new BuckmooException(ResultEnum.PERMISSION_ERROR);
        }
    }
}