package live.lslm.newbuckmoo.form;

import lombok.Data;

@Data
public class SchoolClubForm {
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
     * 邀请码
     */
    private String clubCode;
}