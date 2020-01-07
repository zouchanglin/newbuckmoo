package live.lslm.newbuckmoo.service;


import live.lslm.newbuckmoo.dto.ApproveDTO;

/**
 * 推送消息服务
 */
public interface WechatPushMessageService {
    void approveStatus(ApproveDTO approveDTO);
}
