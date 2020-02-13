package live.lslm.newbuckmoo.aspect.grade;

import live.lslm.newbuckmoo.service.grade.ClubGradService;
import live.lslm.newbuckmoo.service.grade.CompanyGradeService;
import live.lslm.newbuckmoo.service.grade.StudentGradeService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户初始化
 */
@Slf4j
@Aspect
@Component
public class InitOrRecommendUserGrade {
    @Autowired
    private StudentGradeService studentGradeService;

    @Autowired
    private CompanyGradeService companyGradeService;

    @Autowired
    private ClubGradService clubGradService;

    @After("execution(public * live.lslm.newbuckmoo.controller.admin.StudentManageController.auditPass(..))")
    public void initStudentUserGradle(JoinPoint joinPoint){
        String openId = (String) joinPoint.getArgs()[0];
        studentGradeService.registerNewUserInitGrade(openId);
        studentGradeService.registerNewUserRewardGrade(openId);
    }

    @After("execution(public * live.lslm.newbuckmoo.controller.admin.CompanyController.auditPass(..))")
    public void initCompanyUserGradle(JoinPoint joinPoint){
        String openId = (String) joinPoint.getArgs()[0];
        companyGradeService.registerNewUserInitGrade(openId);
        companyGradeService.registerNewUserRewardGrade(openId);
    }

    @After("execution(public * live.lslm.newbuckmoo.controller.admin.ClubManageController.auditPass(..))")
    public void initClubUserGradle(JoinPoint joinPoint){
        String openId = (String) joinPoint.getArgs()[0];
        clubGradService.registerNewUserInitGrade(openId);
        clubGradService.registerNewUserRewardGrade(openId);
    }
}