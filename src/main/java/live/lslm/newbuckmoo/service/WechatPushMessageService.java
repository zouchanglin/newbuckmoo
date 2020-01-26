package live.lslm.newbuckmoo.service;


import live.lslm.newbuckmoo.dto.ClubApproveDTO;
import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.dto.StudentApproveDTO;

/**
 * 推送消息服务
 */
public interface WechatPushMessageService {
    /**
     * 学生身份审核结果通知
     * @param approveDTO 审核结果
     */
    void studentApproveResultStatus(StudentApproveDTO approveDTO);

    /**
     * 企业身份审核结果通知
     * @param approveDTO 审核结果
     */
    void companyApproveResultStatus(CompanyApproveDTO approveDTO);

    /**
     * 社团身份审核结果通知
     * @param approveDTO 审核结果
     */
    void clubApproveResultStatus(ClubApproveDTO approveDTO);

    /**
     * 兼职信息审核结果通知
     * @param positionInfoDTO 审核结果
     */
    void positionApproveResultStatus(PositionInfoDTO positionInfoDTO);
}