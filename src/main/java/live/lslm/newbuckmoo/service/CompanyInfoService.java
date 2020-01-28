package live.lslm.newbuckmoo.service;


import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.form.CompanyAttestationForm;
import live.lslm.newbuckmoo.vo.CompanyVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface CompanyInfoService {
    /**
     * 创建或者更新一条企业信息
     * @param companyAttestationForm 企业信息表单
     * @return 保存后的企业信息
     */
    CompanyApproveDTO createOrUpdateInfo(CompanyAttestationForm companyAttestationForm);

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
    CompanyApproveDTO changeCompanyApprove(String openid, Integer code);

    /**
     * 分页查询审核通过的企业列表
     * @param pageable 分页参数
     * @return 查询分页结果
     */
    Page<CompanyApproveDTO> getCompanyList(Pageable pageable);

    /**
     * 根据openId查询企业信息
     * @param openId openId
     * @return DTO对象
     */
    CompanyApproveDTO getCompanyByOpenId(String openId);

    /**
     * 根据OpenId获得企业VO对象
     * @param openId openID
     * @return VO
     */
    CompanyVO getCompanyVOByOpenId(String openId);
}