package live.lslm.newbuckmoo.service.grade.impl;

import live.lslm.newbuckmoo.entity.RecommendSign;
import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.entity.UserGrade;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.RecommendTypeEnum;
import live.lslm.newbuckmoo.repository.RecommendSignRepository;
import live.lslm.newbuckmoo.repository.StudentInfoRepository;
import live.lslm.newbuckmoo.repository.UserGradeRepository;
import live.lslm.newbuckmoo.service.grade.StudentGradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
public class StudentGradeServiceImpl implements StudentGradeService {
    @Autowired
    private StudentInfoRepository studentInfoRepository;

    @Autowired
    private UserGradeRepository userGradeRepository;

    @Autowired
    private RecommendSignRepository recommendSignRepository;

    @Override
    public void registerNewUserRewardGrade(String openId) {
        RecommendSign recommendSign = recommendSignRepository.findFirstBySignOpenIdAndRecommendType(openId, RecommendTypeEnum.STUDENT_RECOMMEND.getCode());
        if(recommendSign != null){
            String pushOpenId = recommendSign.getPushOpenId();
            Optional<UserGrade> userGradeOptional = userGradeRepository.findById(pushOpenId);
            if(userGradeOptional.isPresent()){
                UserGrade userGrade = userGradeOptional.get();
                //TODO 赠送25积分
                userGrade.setStudentGrade(userGrade.getStudentGrade() + 25);
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
                //TODO 数据库设置学生25积分
                saveGrade.setStudentGrade(25);
                UserGrade save = userGradeRepository.save(saveGrade);
                log.info("【学生初始化积分】 {}", save);
            }
            log.error("【学生初始化积分】学生审核状态不正确");
        }else{
            log.error("【学生初始化积分】 学生信息表中无信息");
        }
    }
}