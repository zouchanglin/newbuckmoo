package live.lslm.newbuckmoo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.utils.EnumUtil;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class StudentApproveDTO {
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

    @JsonIgnore
    public AuditStatusEnum getStatusEnum() {
        return EnumUtil.getByCode(auditStatus, AuditStatusEnum.class);
    }

    @JsonIgnore
    public String getUpdateTime() {
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(new Date(updateTime));
    }
}
