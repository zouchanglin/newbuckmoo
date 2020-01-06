package live.lslm.newbuckmoo.convert;

import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.form.CompanyAttestationForm;

public class CompanyFormToInfoConvert {
    public static void formToCompany(CompanyAttestationForm companyAttestationForm, CompanyInfo saveCompany) {
        saveCompany.setCompanyName(companyAttestationForm.getName());
        saveCompany.setCompanyId(companyAttestationForm.getNumber());
        saveCompany.setCompanyOwnerName(companyAttestationForm.getOwner());
        saveCompany.setCompanyCertificate(companyAttestationForm.getCertificate());
        saveCompany.setCompanyDesc(companyAttestationForm.getDescribe());
    }
}
