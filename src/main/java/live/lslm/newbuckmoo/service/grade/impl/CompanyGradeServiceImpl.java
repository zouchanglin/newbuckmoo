package live.lslm.newbuckmoo.service.grade.impl;

import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.entity.UserGrade;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.repository.CompanyInfoRepository;
import live.lslm.newbuckmoo.repository.UserGradeRepository;
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

    @Override
    public void registerNewUser(String openId) {
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
                //TODO 数据库设置企业100积分
                saveGrade.setCompanyGrade(100);
                UserGrade save = userGradeRepository.save(saveGrade);
                log.info("【企业初始化积分】 {}", save);
            }
            log.error("【企业初始化积分】企业审核状态不正确");
        }else{
            log.error("【企业初始化积分】 企业信息表中无信息");
        }
    }
}