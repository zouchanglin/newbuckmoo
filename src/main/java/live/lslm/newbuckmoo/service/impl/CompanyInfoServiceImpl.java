package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.convert.AttestationConvert;
import live.lslm.newbuckmoo.convert.CompanyInfoToApproveConvert;
import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.form.CompanyAttestationForm;
import live.lslm.newbuckmoo.repository.CompanyInfoRepository;
import live.lslm.newbuckmoo.service.CompanyInfoService;
import live.lslm.newbuckmoo.service.UserBasicInfoService;
import lombok.extern.slf4j.Slf4j;
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
    public CompanyInfo createOrUpdateInfo(CompanyAttestationForm companyAttestationForm) {
        Optional<CompanyInfo> companyInfoFindRet = companyInfoRepository.findById(companyAttestationForm.getOpenid());
        CompanyInfo saveCompany;
        if (companyInfoFindRet.isPresent()) {
            saveCompany = companyInfoFindRet.get();
        } else {
            saveCompany = new CompanyInfo();
            saveCompany.setOpenId(companyAttestationForm.getOpenid());
        }
        AttestationConvert.formToCompany(companyAttestationForm, saveCompany);
        saveCompany.setUpdateTime(System.currentTimeMillis());
        saveCompany.setAuditStatus(AuditStatusEnum.AUDIT_RUNNING.getCode());
        log.info("[CompanyInfoServiceImpl] saveCompany={}", saveCompany);
        return companyInfoRepository.save(saveCompany);
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
}
