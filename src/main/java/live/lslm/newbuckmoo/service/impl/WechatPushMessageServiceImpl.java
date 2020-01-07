package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.dto.ApproveDTO;
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

    @Override
    public void approveStatus(ApproveDTO approveDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        //TODO 微信模板消息ID (模板消息Id设置在配置文件中)
        templateMessage.setTemplateId("微信模板消息Id");
        templateMessage.setToUser(approveDTO.getOpenId());
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first", "亲，审核已经通过了哦"),
                new WxMpTemplateData("keyword1", "亲，审核已经通过了哦"),
                new WxMpTemplateData("keyword2", "亲，审核已经通过了哦"),
                new WxMpTemplateData("keyword3", "亲，审核已经通过了哦"),
                new WxMpTemplateData("keyword4", "亲，审核已经通过了哦"),
                new WxMpTemplateData("keyword5", "亲，审核已经通过了哦"),
                new WxMpTemplateData("remark", "欢迎再次光临")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("[微信模板消息]发送失败 ,{}", e);
        }
    }
}