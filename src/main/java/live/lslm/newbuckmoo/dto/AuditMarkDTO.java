package live.lslm.newbuckmoo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import live.lslm.newbuckmoo.entity.AuditMark;
import live.lslm.newbuckmoo.utils.ConstUtilPoll;
import lombok.Data;

import java.util.Date;

@Data
public class AuditMarkDTO {
    private String openId;

    private String studentMark;

    private String clubMark;

    private Long auditStuTime;

    private Long auditClubTime;

    private Integer auditStuCount;

    private Integer auditClubCount;

    @JsonIgnore
    public String getAuditStuTime(){
        return ConstUtilPoll.dateFormat.format(new Date(auditStuTime));
    }
    @JsonIgnore
    public String getAuditClubTime(){
        return ConstUtilPoll.dateFormat.format(new Date(auditClubTime));
    }


    public static AuditMarkDTO getInitInstance(){
        AuditMarkDTO auditMarkDTO = new AuditMarkDTO();
        auditMarkDTO.setOpenId("");
        auditMarkDTO.setStudentMark("");
        auditMarkDTO.setClubMark("");
        auditMarkDTO.setAuditClubCount(0);
        auditMarkDTO.setAuditStuCount(0);
        auditMarkDTO.setAuditStuTime(System.currentTimeMillis());
        auditMarkDTO.setAuditClubTime(System.currentTimeMillis());
        return auditMarkDTO;
    }

    public static AuditMark getAuditMarkInstance(){
        AuditMark auditMark = new AuditMark();
        auditMark.setOpenId("");
        auditMark.setStudentMark("");
        auditMark.setClubMark("");
        auditMark.setAuditClubCount(0);
        auditMark.setAuditStuCount(0);
        auditMark.setAuditStuTime(System.currentTimeMillis());
        auditMark.setAuditClubTime(System.currentTimeMillis());
        return auditMark;
    }
}