package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.convert.CompanyFormToInfoConvert;
import live.lslm.newbuckmoo.convert.CompanyInfoToApproveConvert;
import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.CompanyAttestationForm;
import live.lslm.newbuckmoo.repository.CompanyInfoRepository;
import live.lslm.newbuckmoo.service.CompanyInfoService;
import live.lslm.newbuckmoo.service.UserBasicInfoService;
import live.lslm.newbuckmoo.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {
    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    @Override
    public Page<CompanyApproveDTO> getCompanyList(Pageable pageable) {
        Page<CompanyInfo> companyInfoPage = companyInfoRepository.findAllByAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageable);
        List<CompanyApproveDTO> dTOList = CompanyInfoToApproveConvert.convert(companyInfoPage.getContent());
        for(CompanyApproveDTO companyApproveDTO: dTOList){
            companyApproveDTO.setUserBasicInfo(userBasicInfoService.getUserBasicInfoByOpenid(companyApproveDTO.getOpenId()));
        }
        return new PageImpl<>(dTOList, pageable, companyInfoPage.getTotalElements());
    }

    @Override
    public CompanyApproveDTO changeCompanyApprove(String openid, Integer code) {
        Optional<CompanyInfo> findResult = companyInfoRepository.findById(openid);
        if(findResult.isPresent()){
            CompanyInfo companyInfo = findResult.get();
            if(companyInfo.getAuditStatus().equals(code)){
                throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
            }else{
                if(EnumUtil.getByCode(code, AuditStatusEnum.class) == null){
                    throw new BuckmooException(ResultEnum.PARAM_ERROR);
                }
                companyInfo.setAuditStatus(code);
                CompanyInfo companyInfoSaved = companyInfoRepository.save(companyInfo);
                log.info("[CompanyInfoServiceImpl] companyInfoSaved={}", companyInfoSaved);
                return convert(companyInfoRepository.save(companyInfoSaved));
            }
        }else {
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public CompanyApproveDTO createOrUpdateInfo(CompanyAttestationForm companyAttestationForm) {
        Optional<CompanyInfo> companyInfoFindRet = companyInfoRepository.findById(companyAttestationForm.getOpenId());
        CompanyInfo saveCompany;
        if (companyInfoFindRet.isPresent()) {
            saveCompany = companyInfoFindRet.get();
            //数据库中存在，但是处于未通过状态才可以继续提交
            //校验是否处于正在审核的状态，未通过审核时才能继续提交
            if(AuditStatusEnum.AUDIT_RUNNING.getCode().equals(saveCompany.getAuditStatus())){
                throw new BuckmooException(ResultEnum.AUDITING_NOT_ALLOWED);
            }
        } else {
            saveCompany = new CompanyInfo();
            saveCompany.setOpenId(companyAttestationForm.getOpenId());
        }
        CompanyFormToInfoConvert.formToCompany(companyAttestationForm, saveCompany);
        saveCompany.setUpdateTime(System.currentTimeMillis());
        saveCompany.setAuditStatus(AuditStatusEnum.AUDIT_RUNNING.getCode());
        log.info("[CompanyInfoServiceImpl] saveCompany={}", saveCompany);
        return convert(companyInfoRepository.save(saveCompany));
    }

    @Override
    public Page<CompanyApproveDTO> getApproveList(Pageable pageable) {
        Page<CompanyInfo> companyInfoPage = companyInfoRepository.findAllByAuditStatusIsNot(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageable);
        List<CompanyApproveDTO> dTOList = CompanyInfoToApproveConvert.convert(companyInfoPage.getContent());
        for(CompanyApproveDTO companyApproveDTO: dTOList){
            companyApproveDTO.setUserBasicInfo(userBasicInfoService.getUserBasicInfoByOpenid(companyApproveDTO.getOpenId()));
        }
        return new PageImpl<>(dTOList, pageable, companyInfoPage.getTotalElements());
    }

    private CompanyApproveDTO convert(CompanyInfo companyInfo){
        CompanyApproveDTO companyApproveDTO = new CompanyApproveDTO();
        BeanUtils.copyProperties(companyInfo, companyApproveDTO);
        UserBasicInfo userBasicInfo = userBasicInfoService.getUserBasicInfoByOpenid(companyInfo.getOpenId());
        if(userBasicInfo == null) throw new BuckmooException(ResultEnum.PARAM_ERROR);
        companyApproveDTO.setUserBasicInfo(userBasicInfo);
        return companyApproveDTO;
    }
}
