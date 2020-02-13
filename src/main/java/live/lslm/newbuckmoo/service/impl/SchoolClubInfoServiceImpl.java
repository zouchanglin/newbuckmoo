package live.lslm.newbuckmoo.service.impl;

import com.google.common.collect.Lists;
import live.lslm.newbuckmoo.dto.AuditMarkDTO;
import live.lslm.newbuckmoo.dto.ClubApproveDTO;
import live.lslm.newbuckmoo.entity.AuditMark;
import live.lslm.newbuckmoo.entity.RecommendSign;
import live.lslm.newbuckmoo.entity.SchoolClubInfo;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.RecommendTypeEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.SchoolClubAttestationForm;
import live.lslm.newbuckmoo.form.club.ClubRecommendSignForm;
import live.lslm.newbuckmoo.repository.*;
import live.lslm.newbuckmoo.service.SchoolClubInfoService;
import live.lslm.newbuckmoo.service.WebSocket;
import live.lslm.newbuckmoo.service.WechatPushMessageService;
import live.lslm.newbuckmoo.utils.EnumUtil;
import live.lslm.newbuckmoo.vo.SchoolClubVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class SchoolClubInfoServiceImpl implements SchoolClubInfoService {
    @Autowired
    private SchoolClubInfoRepository schoolClubInfoRepository;

    @Autowired
    private UserBasicInfoRepository userBasicInfoRepository;

    @Autowired
    private AuditMarkRepository auditMarkRepository;

    @Autowired
    private RecommendSignRepository recommendSignRepository;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private SystemSettingsRepository settingsRepository;

    @Autowired
    private WechatPushMessageService wechatPushMessageService;

    @Override
    public ClubApproveDTO changeClubApprove(String openId, AuditStatusEnum auditSuccess, String auditRemark) {
        Optional<SchoolClubInfo> findResult = schoolClubInfoRepository.findById(openId);
        Integer code = auditSuccess.getCode();
        if(findResult.isPresent()){
            SchoolClubInfo schoolClubInfo = findResult.get();
            if(schoolClubInfo.getAuditStatus().equals(code)){
                throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
            }else{
                schoolClubInfo.setAuditStatus(code);
                SchoolClubInfo schoolClubInfoSaved = schoolClubInfoRepository.save(schoolClubInfo);
                saveAuditRemark(openId, auditRemark);
                return convert(schoolClubInfoSaved);
            }
        }else {
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }

    private void saveAuditRemark(String openId, String auditRemark) {
        Optional<AuditMark> auditMarkOpt = auditMarkRepository.findById(openId);
        AuditMark auditMark = new AuditMark();
        if (auditMarkOpt.isPresent()) {
            BeanUtils.copyProperties(auditMarkOpt.get(), auditMark);
        } else {
            auditMark = AuditMarkDTO.getAuditMarkInstance();
            auditMark.setOpenId(openId);
        }
        auditMark.setAuditClubTime(System.currentTimeMillis());
        auditMark.setAuditClubCount(auditMark.getAuditClubCount() + 1);
        auditMark.setClubMark(auditRemark);
        AuditMark save = auditMarkRepository.save(auditMark);
        log.info("【审核结果存储】 {}", save);
    }

    @Override
    public SchoolClubVO getClubVOByOpenId(String openId) {
        Optional<SchoolClubInfo> clubInfoOpt = schoolClubInfoRepository.findById(openId);
        if(clubInfoOpt.isPresent()){
            SchoolClubInfo schoolClubInfo = clubInfoOpt.get();
            SchoolClubVO schoolClubVO = new SchoolClubVO();
            BeanUtils.copyProperties(schoolClubInfo, schoolClubVO);
            schoolClubVO.setAuditStatusStr(Objects.requireNonNull(
                    EnumUtil.getByCode(schoolClubInfo.getAuditStatus(), AuditStatusEnum.class)).getMessage());
            return schoolClubVO;
        }
        return null;
    }

    @Override
    public ClubApproveDTO getClubInfoByOpenId(String openId) {
        Optional<SchoolClubInfo> clubInfo = schoolClubInfoRepository.findById(openId);
        return clubInfo.map(this::convert).orElse(null);
    }

    @Override
    public Page<ClubApproveDTO> getClubList(Pageable pageable) {
        Page<SchoolClubInfo> schoolClubInfoPage = schoolClubInfoRepository.findAllByAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageable);
        List<SchoolClubInfo> schoolClubInfoList = schoolClubInfoPage.getContent();
        ArrayList<ClubApproveDTO> descList = Lists.newArrayList();

        for(SchoolClubInfo schoolClubInfo: schoolClubInfoList){
            ClubApproveDTO clubApproveDTO = new ClubApproveDTO();
            BeanUtils.copyProperties(schoolClubInfo, clubApproveDTO);
            Optional<UserBasicInfo> basicInfo = userBasicInfoRepository.findById(clubApproveDTO.getOpenId());
            if(basicInfo.isPresent()){
                clubApproveDTO.setUserBasicInfo(basicInfo.get());
            }else{
                throw new BuckmooException(ResultEnum.PARAM_ERROR);
            }
            descList.add(clubApproveDTO);
        }
        return new PageImpl<>(descList, pageable, schoolClubInfoPage.getTotalElements());
    }

    @Override
    public ClubApproveDTO createOrUpdateInfo(SchoolClubAttestationForm schoolClubForm) {
        String openId = schoolClubForm.getOpenId();
        Optional<SchoolClubInfo> findSchoolRet = schoolClubInfoRepository.findById(openId);
        SchoolClubInfo schoolClubInfo;
        if(findSchoolRet.isPresent()){
            //存在
            schoolClubInfo = findSchoolRet.get();
            //数据库中存在，但是处于未通过状态才可以继续提交
            //校验是否处于正在审核的状态，未通过审核时才能继续提交
            if(AuditStatusEnum.AUDIT_RUNNING.getCode().equals(schoolClubInfo.getAuditStatus())){
                throw new BuckmooException(ResultEnum.AUDITING_NOT_ALLOWED);
            }
        }else{
            schoolClubInfo = new SchoolClubInfo();
            schoolClubInfo.setOpenId(openId);
        }
        BeanUtils.copyProperties(schoolClubForm, schoolClubInfo);
        schoolClubInfo.setAuditStatus(AuditStatusEnum.AUDIT_RUNNING.getCode());
        schoolClubInfo.setUpdateTime(System.currentTimeMillis());
        SchoolClubInfo schoolClubInfoSaved = schoolClubInfoRepository.save(schoolClubInfo);
        return convert(schoolClubInfoSaved);
    }

    @Override
    public ClubApproveDTO createStudentInfoByRecommend(ClubRecommendSignForm recommendSignForm) {

        if(!isAuditPassUser(recommendSignForm.getOpenId())){
            log.error("【社团推荐注册】推荐人信息有误！");
            throw new BuckmooException(ResultEnum.RECOMMEND_PUSHER_ERROR);
        }

        ClubApproveDTO clubApproveDTO = createOrUpdateInfo(recommendSignForm);
        RecommendSign recommendSign = new RecommendSign();
        recommendSign.setSignOpenId(recommendSignForm.getOpenId());
        recommendSign.setPushOpenId(recommendSignForm.getRecommendCode());
        recommendSign.setRecommendType(RecommendTypeEnum.CLUB_RECOMMEND.getCode());
        recommendSignRepository.save(recommendSign);

        //通知后台管理
        webSocket.sendMessage("新的社团注册信息有待审核哟 &/admin/approve/club-list");

        String[] split = settingsRepository.getOne("admin_open_id").getSystemValue().split("#");
        wechatPushMessageService.newUserRegister(split);

        //通知微信用户端
        wechatPushMessageService.clubApproveResultStatus(clubApproveDTO);

        return clubApproveDTO;
    }

    @Override
    public Page<ClubApproveDTO> getApproveList(Pageable pageable) {
        Page<SchoolClubInfo> schoolClubInfoPage = schoolClubInfoRepository.findAllByAuditStatusIsNot(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageable);
        List<SchoolClubInfo> schoolClubInfoList = schoolClubInfoPage.getContent();
        ArrayList<ClubApproveDTO> descList = Lists.newArrayList();

        for(SchoolClubInfo schoolClubInfo: schoolClubInfoList){
            ClubApproveDTO clubApproveDTO = new ClubApproveDTO();
            BeanUtils.copyProperties(schoolClubInfo, clubApproveDTO);
            Optional<UserBasicInfo> basicInfo = userBasicInfoRepository.findById(clubApproveDTO.getOpenId());
            if(basicInfo.isPresent()){
                clubApproveDTO.setUserBasicInfo(basicInfo.get());
            }else{
                throw new BuckmooException(ResultEnum.PARAM_ERROR);
            }
            descList.add(clubApproveDTO);
        }
        return new PageImpl<>(descList, pageable, schoolClubInfoPage.getTotalElements());
    }


    private ClubApproveDTO convert(SchoolClubInfo schoolClubInfo){
        ClubApproveDTO clubApproveDTO = new ClubApproveDTO();
        BeanUtils.copyProperties(schoolClubInfo, clubApproveDTO);
        Optional<UserBasicInfo> userBasicInfo = userBasicInfoRepository.findById(schoolClubInfo.getOpenId());
        if(userBasicInfo.isPresent()){
            clubApproveDTO.setUserBasicInfo(userBasicInfo.get());
            Optional<AuditMark> auditMarkFind = auditMarkRepository.findById(schoolClubInfo.getOpenId());
            if(auditMarkFind.isPresent()){
                AuditMark auditMark = auditMarkFind.get();
                AuditMarkDTO auditMarkDTO = new AuditMarkDTO();
                BeanUtils.copyProperties(auditMark, auditMarkDTO);
                clubApproveDTO.setAuditMarkDTO(auditMarkDTO);
            }else{
                clubApproveDTO.setAuditMarkDTO(AuditMarkDTO.getInitInstance());
            }
            return clubApproveDTO;
        }else {
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public Boolean isAuditPassUser(String openId) {
        Optional<SchoolClubInfo> schoolClubInfo = schoolClubInfoRepository.findById(openId);
        return schoolClubInfo.filter(clubInfo -> AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(clubInfo.getAuditStatus())).isPresent();
    }
}