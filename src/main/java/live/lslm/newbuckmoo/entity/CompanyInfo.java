package live.lslm.newbuckmoo.entity;

import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 企业信息
 */
@Data
@Entity
@DynamicUpdate
public class CompanyInfo implements Serializable {
    private static final long serialVersionUID = -5293825907880872174L;
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
    private Integer auditStatus = AuditStatusEnum.AUDIT_RUNNING.getCode();

    /**
     * 信息更新时间
     */
    private Long updateTime = System.currentTimeMillis();
}