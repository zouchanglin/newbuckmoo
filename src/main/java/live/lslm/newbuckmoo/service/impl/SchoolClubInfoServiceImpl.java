package live.lslm.newbuckmoo.service.impl;

import com.google.common.collect.Lists;
import live.lslm.newbuckmoo.dto.ClubApproveDTO;
import live.lslm.newbuckmoo.entity.SchoolClubInfo;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.SchoolClubAttestationForm;
import live.lslm.newbuckmoo.repository.SchoolClubInfoRepository;
import live.lslm.newbuckmoo.repository.UserBasicInfoRepository;
import live.lslm.newbuckmoo.service.SchoolClubInfoService;
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
import java.util.Optional;

@Slf4j
@Service
public class SchoolClubInfoServiceImpl implements SchoolClubInfoService {
    @Autowired
    private SchoolClubInfoRepository schoolClubInfoRepository;

    @Autowired
    private UserBasicInfoRepository userBasicInfoRepository;

    @Override
    public SchoolClubVO getClubVOByOpenId(String openId) {
        Optional<SchoolClubInfo> clubInfoOpt = schoolClubInfoRepository.findById(openId);
        if(clubInfoOpt.isPresent()){
            SchoolClubInfo schoolClubInfo = clubInfoOpt.get();
            SchoolClubVO schoolClubVO = new SchoolClubVO();
            BeanUtils.copyProperties(schoolClubInfo, schoolClubVO);
            schoolClubVO.setAuditStatusStr(EnumUtil.getByCode(schoolClubInfo.getAuditStatus(), AuditStatusEnum.class).getMessage());
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
    public ClubApproveDTO changeClubApprove(String openid, Integer code) {
        Optional<SchoolClubInfo> findResult = schoolClubInfoRepository.findById(openid);
        if(findResult.isPresent()){
            SchoolClubInfo schoolClubInfo = findResult.get();
            if(schoolClubInfo.getAuditStatus().equals(code)){
                throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
            }else{
                if(EnumUtil.getByCode(code, AuditStatusEnum.class) == null){
                    throw new BuckmooException(ResultEnum.PARAM_ERROR);
                }
                schoolClubInfo.setAuditStatus(code);
                SchoolClubInfo schoolClubInfoSaved = schoolClubInfoRepository.save(schoolClubInfo);
                log.info("[SchoolClubInfoServiceImpl] schoolClubInfoSaved={}", schoolClubInfoSaved);
                return convert(schoolClubInfoSaved);
            }
        }else {
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
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
            return clubApproveDTO;
        }else {
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }
}