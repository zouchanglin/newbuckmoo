package live.lslm.newbuckmoo.form;


import lombok.Data;

@Data
public class PositionInfoForm implements BasicForm{
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
    private Integer positionClearingWay;

    /**
     * 职位发布企业
     */
    private String positionCompanyId;

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
     * 用户openId
     */
    private String openId;
}