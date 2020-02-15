package live.lslm.newbuckmoo.service.grade.impl;

import live.lslm.newbuckmoo.entity.*;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.RecommendTypeEnum;
import live.lslm.newbuckmoo.form.UserBuyGradeForm;
import live.lslm.newbuckmoo.repository.RecommendSignRepository;
import live.lslm.newbuckmoo.repository.SchoolClubInfoRepository;
import live.lslm.newbuckmoo.repository.UserGradeRepository;
import live.lslm.newbuckmoo.service.admin.SettingEnum;
import live.lslm.newbuckmoo.service.admin.SettingService;
import live.lslm.newbuckmoo.service.grade.ClubGradService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
public class ClubGradServiceImpl implements ClubGradService {
    @Autowired
    private SchoolClubInfoRepository schoolClubInfoRepository;

    @Autowired
    private UserGradeRepository userGradeRepository;

    @Autowired
    private RecommendSignRepository recommendSignRepository;

    @Autowired
    private SettingService settingService;

    @Override
    public GeneralOrder createBuyGradeOrder(UserBuyGradeForm userBuyGradeForm) {
        return null;
    }

    @Override
    public void registerNewUserRewardGrade(String openId) {
        RecommendSign recommendSign = recommendSignRepository.findFirstBySignOpenIdAndRecommendType(openId, RecommendTypeEnum.CLUB_RECOMMEND.getCode());
        if(recommendSign != null){
            String pushOpenId = recommendSign.getPushOpenId();
            Optional<UserGrade> userGradeOptional = userGradeRepository.findById(pushOpenId);
            if(userGradeOptional.isPresent()){
                UserGrade userGrade = userGradeOptional.get();
                SystemSettings newClubGradeSetting = settingService.getOneSetting(SettingEnum.RECOMMEND_CLUB);
                userGrade.setClubGrade(userGrade.getClubGrade() + Integer.parseInt(newClubGradeSetting.getSystemValue()));
                userGradeRepository.save(userGrade);
            }else{
                log.error("【奖励积分：推荐社团注册通过】积分表中找不到推荐人");
            }
        }else {
            log.error("【奖励积分：推荐社团注册通过】推荐表中找不到推荐人");
        }
    }

    @Override
    public void registerNewUserInitGrade(String openId) {
        if(StringUtils.isEmpty(openId)) return;
        Optional<SchoolClubInfo> clubInfoOptional = schoolClubInfoRepository.findById(openId);
        if(clubInfoOptional.isPresent()){
            SchoolClubInfo schoolClubInfo = clubInfoOptional.get();
            if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(schoolClubInfo.getAuditStatus())){
                Optional<UserGrade> userGrade = userGradeRepository.findById(openId);
                UserGrade saveGrade = new UserGrade();
                if(userGrade.isPresent()){
                    BeanUtils.copyProperties(userGrade.get(), saveGrade);
                }else{
                    saveGrade.setOpenId(openId);
                    saveGrade.setStudentGrade(0);
                    saveGrade.setCompanyGrade(0);
                }
                SystemSettings newClubGradeSetting = settingService.getOneSetting(SettingEnum.NEW_CLUB);
                saveGrade.setClubGrade(Integer.parseInt(newClubGradeSetting.getSystemValue()));
                UserGrade save = userGradeRepository.save(saveGrade);
                log.info("【社团初始化积分】 {}", save);
            }else{
                log.error("【社团初始化积分】社团审核状态不正确");
            }
        }else{
            log.error("【社团初始化积分】 社团信息表中无信息");
        }
    }
}