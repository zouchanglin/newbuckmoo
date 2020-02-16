package live.lslm.newbuckmoo.vo;

import lombok.Data;

@Data
public class BuyGradeOrderVO {
    private String orderId;
    /**
     * 订单的名称
     */
    private String orderName;

    /**
     * 订单类型
     */
    private String orderTypeStr;

    /**
     * 订单名称
     */
    private String gradeName;

    /**
     * 积分数量
     */
    private Integer gradeNum;

    /**
     * 订单金额
     */
    private String orderMoneyStr;

    /**
     * 支付状态
     */
    private String orderPayStatusStr;

    /**
     * 创建时间
     */
    private Long createTime;
    private String createTimeStr;

    /**
     * 更新时间
     */
    private Long updateTime;
    private String updateTimeStr;
}