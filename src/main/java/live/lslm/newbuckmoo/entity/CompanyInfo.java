package live.lslm.newbuckmoo.entity;

import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 企业信息
 */
@Data
@Entity
@DynamicUpdate
public class CompanyInfo {
    @Id
    private String openId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 工商注册号
     */
    private String companyId;

    /**
     * 法定代表人姓名
     */
    private String companyOwnerName;

    /**
     * 营业执照路径
     */
    private String companyCertificate;

    /**
     * 经营范围描述
     */
    private String companyDesc;

    /**
     * 审核状态
     */
    private Integer auditStatus = AuditStatusEnum.AUDIT_FUTURE.getCode();
}