package live.lslm.newbuckmoo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import live.lslm.newbuckmoo.entity.CategoryInfo;
import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ClearingWayEnum;
import live.lslm.newbuckmoo.enums.PositionTopEnum;
import live.lslm.newbuckmoo.utils.EnumUtil;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class PositionInfoDTO {
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
    private Integer[] positionCategory;

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
     * 用户原始信息
     */
    private UserBasicInfo userBasicInfo;

    /**
     * 企业信息
     */
    private CompanyInfo companyInfo;

    /**
     * 职位类型
     */
    private CategoryInfo[] categoryList;

    /**
     * 审核结论
     */
    private String auditRemark;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    @JsonIgnore
    public AuditStatusEnum getAuditStatusEnum(){
        return EnumUtil.getByCode(auditStatus, AuditStatusEnum.class);
    }

    @JsonIgnore
    public PositionTopEnum getIsTop() {
        return EnumUtil.getByCode(positionTop, PositionTopEnum.class);
    }

    @JsonIgnore
    public ClearingWayEnum getClearingWayEnum() {
        return EnumUtil.getByCode(positionClearingWay, ClearingWayEnum.class);
    }

    @JsonIgnore
    public String getUpdateTime() {
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(updateTime));
    }


    @JsonIgnore
    public String getCreateTime() {
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date(createTime));
    }
}
