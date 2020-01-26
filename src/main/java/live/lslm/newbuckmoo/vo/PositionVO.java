package live.lslm.newbuckmoo.vo;

import live.lslm.newbuckmoo.entity.CategoryInfo;
import lombok.Data;

/**
 * 兼职展示视图对象
 */
@Data
public class PositionVO {
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
     * 结算方式
     */
    private String positionClearingWayStr;

    /**
     * 职位发布企业 展示企业详情 URL准备
     */
    private String positionCompanyId;

    /**
     * 职位发布企业名称
     */
    private String positionCompanyName;

    /**
     * 兼职信息是否置顶
     */
    private Integer positionTop;

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
     * 职位标签
     */
    private CategoryInfo[] categoryList;

    /**
     * 职位浏览量
     */
    private Long positionBrowse;

    /**
     * 创建时间
     */
    private String createTimeStr;
}