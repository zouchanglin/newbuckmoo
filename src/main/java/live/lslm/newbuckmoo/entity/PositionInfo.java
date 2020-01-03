package live.lslm.newbuckmoo.entity;

import live.lslm.newbuckmoo.enums.ClearingWayEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 职位信息
 */
@Entity
@Data
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
    private Integer positionMoney;

    /**
     * 结算方式：默认 日结
     */
    private Integer positionClearingWay = ClearingWayEnum.DAY_CLEARING.getCode();

    /**
     * 职位发布企业
     */
    private String positionCompanyId;

    /**
     * 职位发布时间 2020-01-10
     */
    private Long positionTime;

    /**
     * 兼职信息是否置顶
     */
    private Integer positionTop;

    /**
     * 职位类型
     */
    private Integer positionCategory;

    /**
     * 职位描述
     */
    private String positionDesc;

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
}