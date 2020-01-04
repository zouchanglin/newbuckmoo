package live.lslm.newbuckmoo.service;


import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.form.CompanyAttestationForm;

public interface CompanyInfoService {
    CompanyInfo createOrUpdateInfo(CompanyAttestationForm companyAttestationForm);
}