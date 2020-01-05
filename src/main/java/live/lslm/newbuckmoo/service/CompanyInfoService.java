package live.lslm.newbuckmoo.service;


import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.form.CompanyAttestationForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface CompanyInfoService {
    /**
     * 创建或者更新一条企业信息
     * @param companyAttestationForm 企业信息表单
     * @return 保存后的企业信息
     */
    CompanyInfo createOrUpdateInfo(CompanyAttestationForm companyAttestationForm);

    /**
     * 分页查询待审核企业列表
     * @param pageable 分页参数
     * @return 查询分页结果
     */
    Page<CompanyApproveDTO> getApproveList(Pageable pageable);

    /**
     * 企业通过认证/不通过认证
     * @param openid 企业管理员OpenId
     * @param code 认证状态
     */
    void changeCompanyApprove(String openid, Integer code);
}