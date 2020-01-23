package live.lslm.newbuckmoo.form;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PositionInfoForm implements BasicForm{

    private String positionId;

    /**
     * 职位名称
     */
    @NotEmpty(message = "职位名称必填")
    private String positionName;

    /**
     * 薪酬：2000/天
     */
    @NotEmpty(message = "薪酬必填（可填面议）")
    private String positionMoney;

    /**
     * 结算方式：默认 日结
     */
    @NotNull(message = "结算方式必填")
    private Integer positionClearingWay;

    /**
     * 职位发布企业
     */
    @NotEmpty(message = "发布企业Id必填")
    private String positionCompanyId;

    /**
     * 兼职信息是否置顶
     */
    @NotNull(message = "是否置顶必填")
    private Integer positionTop;

    /**
     * 职位类型
     */
    @NotEmpty(message = "职位标签必填")
    private Integer[] positionCategory;

    /**
     * 职位描述
     */
    @NotEmpty(message = "职位描述必填")
    private String positionDesc;

    /**
     * 职位地点
     */
    @NotEmpty(message = "职位地点必填")
    private String positionAddress;

    /**
     * 职位招聘人数
     */
    @NotNull(message = "职位人数必填")
    private Integer positionPeopleNum;

    /**
     * 职位联系方式
     */
    @NotEmpty(message = "职位联系方式必填")
    private String positionPhone;

    /**
     * 用户openId
     */
    @NotEmpty(message = "openId丢失")
    private String openId;
}