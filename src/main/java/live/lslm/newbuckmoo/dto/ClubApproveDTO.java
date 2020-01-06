package live.lslm.newbuckmoo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.utils.EnumUtil;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ClubApproveDTO {


    private String openId;
    /**
     * 社团名称
     */
    private String clubName;
    /**
     * 社团描述
     */
    private String clubDesc;

    /**
     * 所属学校
     */
    private String schoolName;

    /**
     * 社团负责人姓名
     */
    private String ownerName;

    /**
     * 审核状态
     */
    private Integer auditStatus = AuditStatusEnum.AUDIT_RUNNING.getCode();

    /**
     * 申请时间
     */
    private Long updateTime = System.currentTimeMillis();

    /**
     * 邀请码
     */
    private String clubCode;

    /**
     * 用户基本信息
     */
    private UserBasicInfo userBasicInfo;

    @JsonIgnore
    public String getUpdateTime() {
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date(updateTime));
    }

    @JsonIgnore
    public AuditStatusEnum getStatusEnum(){
        return EnumUtil.getByCode(auditStatus, AuditStatusEnum.class);
    }
}
