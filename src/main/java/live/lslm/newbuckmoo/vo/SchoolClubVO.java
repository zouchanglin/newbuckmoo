package live.lslm.newbuckmoo.vo;


import lombok.Data;

@Data
public class SchoolClubVO {
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
    private Integer auditStatus;

    /**
     * 审核状态
     */
    private String auditStatusStr;

    /**
     * 申请时间
     */
    private Long updateTime;
}
