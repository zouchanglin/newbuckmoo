package live.lslm.newbuckmoo.service.grade.impl;

import live.lslm.newbuckmoo.entity.*;
import live.lslm.newbuckmoo.enums.*;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.UserBuyGradeForm;
import live.lslm.newbuckmoo.repository.RecommendSignRepository;
import live.lslm.newbuckmoo.repository.StudentInfoRepository;
import live.lslm.newbuckmoo.repository.UserGradeRepository;
import live.lslm.newbuckmoo.service.StudentsInfoService;
import live.lslm.newbuckmoo.service.admin.SettingEnum;
import live.lslm.newbuckmoo.service.admin.SettingService;
import live.lslm.newbuckmoo.service.grade.GradeComboService;
import live.lslm.newbuckmoo.service.grade.StudentGradeService;
import live.lslm.newbuckmoo.service.grade.UserGradeService;
import live.lslm.newbuckmoo.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
public class StudentGradeServiceImpl implements StudentGradeService {
    @Autowired
    private StudentInfoRepository studentInfoRepository;

    @Autowired
    private StudentsInfoService studentsInfoService;

    @Autowired
    private UserGradeRepository userGradeRepository;

    @Autowired
    private RecommendSignRepository recommendSignRepository;

    @Autowired
    private SettingService settingService;

    @Autowired
    private GradeComboService gradeComboService;

    @Override
    public GeneralOrder createBuyGradeOrder(UserBuyGradeForm userBuyGradeForm) {
        String openId = userBuyGradeForm.getOpenId();
        if(!studentsInfoService.isAuditPassUser(openId)){
            log.error("【学生购买积分套餐】非学生用户/学生审核未通过");
            throw new BuckmooException(ResultEnum.PERMISSION_ERROR);
        }
        GradeCombo gradeCombo = gradeComboService.getOneComboById(userBuyGradeForm.getGradeComboId());

        GeneralOrder order = new GeneralOrder();
        order.setOrderId(KeyUtil.genUniqueKey());
        order.setOrderMoney(BigDecimal.valueOf(gradeCombo.getGradeMoney()));
        order.setOrderName(String.format("购买: %s套餐", gradeCombo.getGradeName()));
        order.setOrderOpenId(userBuyGradeForm.getOpenId());
        order.setOrderPayStatus(PayStatusEnum.WILL_PAY.getCode());
        order.setOrderType(OrderTypeEnum.COMPANY_BUY_GRADE.getCode());
        return order;
    }

    @Override
    public void registerNewUserRewardGrade(String openId) {
        RecommendSign recommendSign = recommendSignRepository.findFirstBySignOpenIdAndRecommendType(openId, RecommendTypeEnum.STUDENT_RECOMMEND.getCode());
        if(recommendSign != null){
            String pushOpenId = recommendSign.getPushOpenId();
            Optional<UserGrade> userGradeOptional = userGradeRepository.findById(pushOpenId);
            if(userGradeOptional.isPresent()){
                UserGrade userGrade = userGradeOptional.get();
                SystemSettings setting = settingService.getOneSetting(SettingEnum.RECOMMEND_STUDENT);
                userGrade.setStudentGrade(userGrade.getStudentGrade() + Integer.parseInt(setting.getSystemValue()));
                userGradeRepository.save(userGrade);
            }else{
                log.error("【奖励积分：推荐学生注册通过】积分表中找不到推荐人");
            }
        }else {
            log.error("【奖励积分：推荐学生注册通过】推荐表中找不到推荐人");
        }
    }

    @Override
    public void registerNewUserInitGrade(String openId) {
        if(StringUtils.isEmpty(openId)) return;
        Optional<StudentInfo> studentInfoOptional = studentInfoRepository.findById(openId);
        if(studentInfoOptional.isPresent()){
            StudentInfo studentInfo = studentInfoOptional.get();
            if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(studentInfo.getAuditStatus())){
                Optional<UserGrade> userGrade = userGradeRepository.findById(openId);
                UserGrade saveGrade = new UserGrade();
                if(userGrade.isPresent()){
                    BeanUtils.copyProperties(userGrade.get(), saveGrade);
                }else{
                    saveGrade.setOpenId(openId);
                    saveGrade.setClubGrade(0);
                    saveGrade.setCompanyGrade(0);
                }
                SystemSettings setting = settingService.getOneSetting(SettingEnum.NEW_STUDENT);
                saveGrade.setStudentGrade(Integer.parseInt(setting.getSystemValue()));
                UserGrade save = userGradeRepository.save(saveGrade);
                log.info("【学生初始化积分】 {}", save);
            }
            log.error("【学生初始化积分】学生审核状态不正确");
        }else{
            log.error("【学生初始化积分】 学生信息表中无信息");
        }
    }
}