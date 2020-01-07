package live.lslm.newbuckmoo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.utils.EnumUtil;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class CompanyApproveDTO implements ApproveDTO{
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
    private Integer auditStatus;

    /**
     * 信息更新时间
     */
    private Long updateTime;


    /**
     * 用户基本信息
     */
    private UserBasicInfo userBasicInfo;

    @JsonIgnore
    public AuditStatusEnum getStatusEnum(){
        return EnumUtil.getByCode(auditStatus, AuditStatusEnum.class);
    }


    @JsonIgnore
    public String getUpdateTime() {
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date(updateTime));
    }
}