package live.lslm.newbuckmoo.service.grade.impl;

import live.lslm.newbuckmoo.entity.SchoolClubInfo;
import live.lslm.newbuckmoo.entity.UserGrade;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.repository.SchoolClubInfoRepository;
import live.lslm.newbuckmoo.repository.UserGradeRepository;
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

    @Override
    public void registerNewUser(String openId) {
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
                //TODO 数据库设置社团0积分
                saveGrade.setClubGrade(0);
                UserGrade save = userGradeRepository.save(saveGrade);
                log.info("【社团初始化积分】 {}", save);
            }
            log.error("【社团初始化积分】社团审核状态不正确");
        }else{
            log.error("【社团初始化积分】 社团信息表中无信息");
        }
    }
}
