package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.controller.AttestationController;
import live.lslm.newbuckmoo.convert.AttestationConvert;
import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.form.CompanyAttestationForm;
import live.lslm.newbuckmoo.repository.CompanyInfoRepository;
import live.lslm.newbuckmoo.service.CompanyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {
    @Autowired
    private CompanyInfoRepository companyInfoRepository;

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
        saveCompany.setAuditStatus(AuditStatusEnum.AUDIT_RUNNING.getCode());
        log.info("[CompanyInfoServiceImpl] saveCompany={}", saveCompany);
        return companyInfoRepository.save(saveCompany);
    }
}
