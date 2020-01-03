package live.lslm.newbuckmoo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

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
}