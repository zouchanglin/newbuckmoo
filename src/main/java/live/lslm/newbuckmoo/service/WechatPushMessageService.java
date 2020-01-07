package live.lslm.newbuckmoo.service;


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
}
