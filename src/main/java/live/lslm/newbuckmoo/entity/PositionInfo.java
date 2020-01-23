package live.lslm.newbuckmoo.entity;

import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ClearingWayEnum;
import live.lslm.newbuckmoo.enums.PositionTopEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 职位信息
 */
@Data
@Entity
@DynamicUpdate
public class PositionInfo {
    @Id
    private String positionId;

    /**
     * 职位名称
     */
    private String positionName;

    /**
     * 薪酬：2000/天
     */
    private String positionMoney;

    /**
     * 结算方式：默认 日结
     */
    private Integer positionClearingWay = ClearingWayEnum.DAY_CLEARING.getCode();

    /**
     * 职位发布企业
     */
    private String positionCompanyId;

    /**
     * 兼职信息是否置顶
     */
    private Integer positionTop = PositionTopEnum.NTO_TOP.getCode();

    /**
     * 职位类型（做成标签的形式：1#5#6）以#分割
     */
    private String positionCategory;

    /**
     * 职位描述
     */
    private String positionDesc;

    /**
     * 职位地点
     */
    private String positionAddress;

    /**
     * 职位招聘人数
     */
    private Integer positionPeopleNum;

    /**
     * 职位联系方式
     */
    private String positionPhone;

    /**
     * 职位浏览量
     */
    private Long positionBrowse;

    /**
     * 用户openId
     */
    private String openId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 审核状态
     */
    private Integer auditStatus = AuditStatusEnum.AUDIT_RUNNING.getCode();

    /**
     * 审核结论
     */
    private String auditRemark;
}