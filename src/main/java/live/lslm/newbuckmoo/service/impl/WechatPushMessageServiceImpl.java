package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.config.WechatAccountConfig;
import live.lslm.newbuckmoo.dto.ApproveDTO;
import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.service.WechatPushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Slf4j
@Service
public class WechatPushMessageServiceImpl implements WechatPushMessageService {
    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private WechatAccountConfig wechatAccountConfig;

    @Override
    public void studentApproveResultStatus(StudentApproveDTO approveDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        String auditTemplateId = wechatAccountConfig.getTemplateId().get("auditResult");
        templateMessage.setTemplateId(auditTemplateId);
        templateMessage.setToUser(approveDTO.getOpenId());
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "亲，审核已经通过了哦"),
                new WxMpTemplateData("keyword1", approveDTO.getStudentName()),
                new WxMpTemplateData("keyword2", "学生身份认证"),
                new WxMpTemplateData("keyword3", "审核通过"),
                new WxMpTemplateData("keyword4", approveDTO.getUpdateTime()),
                new WxMpTemplateData("remark", "欢迎参加我们的研学旅行、企业或社团的活动以及做兼职等等哦~")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("[微信模板消息]发送失败 ,{}", e);
        }
    }
}