package live.lslm.newbuckmoo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.utils.ConstUtilPoll;
import live.lslm.newbuckmoo.utils.EnumUtil;
import lombok.Data;

import java.util.Date;

@Data
public class StudentApproveDTO implements ApproveDTO{
    private String openId;

    /**
     * 学生学号
     */
    private String studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生证照片
     */
    private String studentCertificate;

    /**
     * 所在学校
     */
    private String studentSchool;

    /**
     * 简历Id
     */
    private String studentResume;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 信息更新时间
     */
    private Long updateTime;

    /**
     * 用户原始信息
     */
    private UserBasicInfo userBasicInfo;

    @JsonIgnore
    public AuditStatusEnum getStatusEnum() {
        return EnumUtil.getByCode(auditStatus, AuditStatusEnum.class);
    }

    @JsonIgnore
    public String getUpdateTime() {
        return ConstUtilPoll.dateFormat.format(new Date(updateTime));
    }
}
