package live.lslm.newbuckmoo.vo;

import lombok.Data;

@Data
public class CompanyVO {
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
    private Integer auditStatus;

    /**
     * 审核状态String
     */
    private String  auditStatusStr;

    /**
     * 信息更新时间
     */
    private Long updateTime;
}
