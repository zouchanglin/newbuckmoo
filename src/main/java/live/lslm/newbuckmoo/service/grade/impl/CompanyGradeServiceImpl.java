package live.lslm.newbuckmoo.service.grade.impl;

import live.lslm.newbuckmoo.entity.*;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.RecommendTypeEnum;
import live.lslm.newbuckmoo.form.UserBuyGradeForm;
import live.lslm.newbuckmoo.repository.CompanyInfoRepository;
import live.lslm.newbuckmoo.repository.RecommendSignRepository;
import live.lslm.newbuckmoo.repository.UserGradeRepository;
import live.lslm.newbuckmoo.service.admin.SettingEnum;
import live.lslm.newbuckmoo.service.admin.SettingService;
import live.lslm.newbuckmoo.service.grade.CompanyGradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@Service
public class CompanyGradeServiceImpl implements CompanyGradeService {
    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private UserGradeRepository userGradeRepository;

    @Autowired
    private RecommendSignRepository recommendSignRepository;

    @Autowired
    private SettingService settingService;

    @Override
    public void finishOrderAndUpdateUserGrade(GeneralOrder generalOrder, String notifyData) {

    }

    @Override
    public GeneralOrder createBuyGradeOrder(UserBuyGradeForm userBuyGradeForm) {
        return null;
    }

    @Override
    public void registerNewUserRewardGrade(String openId) {
        RecommendSign recommendSign = recommendSignRepository.findFirstBySignOpenIdAndRecommendType(openId, RecommendTypeEnum.COMPANY_RECOMMEND.getCode());
        if(recommendSign != null){
            String pushOpenId = recommendSign.getPushOpenId();
            Optional<UserGrade> userGradeOptional = userGradeRepository.findById(pushOpenId);
            if(userGradeOptional.isPresent()){
                UserGrade userGrade = userGradeOptional.get();
                SystemSettings newCompanyGradeSetting = settingService.getOneSetting(SettingEnum.RECOMMEND_COMPANY);
                userGrade.setCompanyGrade(userGrade.getCompanyGrade() + Integer.parseInt(newCompanyGradeSetting.getSystemValue()));
                userGradeRepository.save(userGrade);
            }else{
                log.error("【奖励积分：推荐企业注册通过】积分表中找不到推荐人");
            }
        }else {
            log.error("【奖励积分：推荐企业注册通过】推荐表中找不到推荐人");
        }
    }

    @Override
    public void registerNewUserInitGrade(String openId) {
        if(StringUtils.isEmpty(openId)) return;
        Optional<CompanyInfo> companyInfoOptional = companyInfoRepository.findById(openId);
        if(companyInfoOptional.isPresent()){
            CompanyInfo companyInfo = companyInfoOptional.get();
            if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(companyInfo.getAuditStatus())){
                Optional<UserGrade> userGrade = userGradeRepository.findById(openId);
                UserGrade saveGrade = new UserGrade();
                if(userGrade.isPresent()){
                    BeanUtils.copyProperties(userGrade.get(), saveGrade);
                }else{
                    saveGrade.setOpenId(openId);
                    saveGrade.setStudentGrade(0);
                    saveGrade.setClubGrade(0);
                }
                SystemSettings newCompanyGradeSetting = settingService.getOneSetting(SettingEnum.NEW_COMPANY);
                saveGrade.setCompanyGrade(Integer.parseInt(newCompanyGradeSetting.getSystemValue()));
                UserGrade save = userGradeRepository.save(saveGrade);
                log.info("【企业初始化积分】 {}", save);
            }
            log.error("【企业初始化积分】企业审核状态不正确");
        }else{
            log.error("【企业初始化积分】 企业信息表中无信息");
        }
    }
}
