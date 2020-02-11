package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.config.WechatAccountConfig;
import live.lslm.newbuckmoo.dto.*;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
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
        List<WxMpTemplateData> data;
        Integer auditStatus = approveDTO.getAuditStatus();
        if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(auditStatus)){
            //通过
            data = Arrays.asList(
                    new WxMpTemplateData("first", approveDTO.getAuditMarkDTO().getStudentMark()),
                    new WxMpTemplateData("keyword1", approveDTO.getStudentName()),
                    new WxMpTemplateData("keyword2", "学生身份认证"),
                    new WxMpTemplateData("keyword3", "审核通过"),
                    new WxMpTemplateData("keyword4", approveDTO.getAuditMarkDTO().getAuditStuTime()),
                    new WxMpTemplateData("remark", "欢迎参加我们的研学旅行、企业或社团的活动以及做兼职等等哦！")
            );
        }else if(AuditStatusEnum.AUDIT_FAILED.getCode().equals(auditStatus)){
            //未通过
            data = Arrays.asList(
                    new WxMpTemplateData("first", approveDTO.getAuditMarkDTO().getStudentMark()),
                    new WxMpTemplateData("keyword1", approveDTO.getStudentName()),
                    new WxMpTemplateData("keyword2", "学生身份认证"),
                    new WxMpTemplateData("keyword3", "审核未通过"),
                    new WxMpTemplateData("keyword4", approveDTO.getAuditMarkDTO().getAuditStuTime()),
                    new WxMpTemplateData("remark", "请检查相关信息是否正确，更正后重新提交，以便工作人员快速审核"));
        }else{
            //审核中
            data = Arrays.asList(
                    new WxMpTemplateData("first", "学生信息正在审核中"),
                    new WxMpTemplateData("keyword1", approveDTO.getStudentName()),
                    new WxMpTemplateData("keyword2", "学生身份认证"),
                    new WxMpTemplateData("keyword3", "审核进行中"),
                    new WxMpTemplateData("keyword4", "-"),
                    new WxMpTemplateData("remark", "学生相关信息正在审核中，1-2个工作日内通知审核结果"));
        }
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】发送失败 ,{}", e);
        }
    }

    @Override
    public void companyApproveResultStatus(CompanyApproveDTO approveDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        String auditTemplateId = wechatAccountConfig.getTemplateId().get("auditResult");
        templateMessage.setTemplateId(auditTemplateId);
        templateMessage.setToUser(approveDTO.getOpenId());
        Integer auditStatus = approveDTO.getAuditStatus();
        List<WxMpTemplateData> data;
        if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(auditStatus)){
            //通过
            data = Arrays.asList(
                    new WxMpTemplateData("first", approveDTO.getAuditMarkDTO().getCompanyMark()),
                    new WxMpTemplateData("keyword1", approveDTO.getCompanyName()),
                    new WxMpTemplateData("keyword2", "企业身份认证"),
                    new WxMpTemplateData("keyword3", "审核通过"),
                    new WxMpTemplateData("keyword4", approveDTO.getAuditMarkDTO().getAuditCompanyTime()),
                    new WxMpTemplateData("remark", "欢迎您的加入，您可以在这里发布兼职，发布热门活动！")
            );
        }else if(AuditStatusEnum.AUDIT_FAILED.getCode().equals(auditStatus)){
            //未通过
            data = Arrays.asList(
                    new WxMpTemplateData("first", approveDTO.getAuditMarkDTO().getCompanyMark()),
                    new WxMpTemplateData("keyword1", approveDTO.getCompanyName()),
                    new WxMpTemplateData("keyword2", "企业身份认证"),
                    new WxMpTemplateData("keyword3", "审核未通过"),
                    new WxMpTemplateData("keyword4", approveDTO.getAuditMarkDTO().getAuditCompanyTime()),
                    new WxMpTemplateData("remark", "请检查相关信息是否正确，更正后重新提交，以便工作人员快速审核"));
        }else{
            //审核中
            data = Arrays.asList(
                    new WxMpTemplateData("first", "企业相关信息正在审核中"),
                    new WxMpTemplateData("keyword1", approveDTO.getCompanyName()),
                    new WxMpTemplateData("keyword2", "企业身份认证"),
                    new WxMpTemplateData("keyword3", "审核进行中"),
                    new WxMpTemplateData("keyword4", "-"),
                    new WxMpTemplateData("remark", "企业相关信息正在审核中，1-2个工作日内通知审核结果"));
        }

        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】发送失败 ,{}", e);
        }
    }

    @Override
    public void clubApproveResultStatus(ClubApproveDTO approveDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        String auditTemplateId = wechatAccountConfig.getTemplateId().get("auditResult");
        templateMessage.setTemplateId(auditTemplateId);
        templateMessage.setToUser(approveDTO.getOpenId());
        List<WxMpTemplateData> data;
        Integer auditStatus = approveDTO.getAuditStatus();
        if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(auditStatus)){
            //通过
            data = Arrays.asList(
                    new WxMpTemplateData("first", approveDTO.getAuditMarkDTO().getClubMark()),
                    new WxMpTemplateData("keyword1", approveDTO.getClubName()),
                    new WxMpTemplateData("keyword2", "社团身份认证"),
                    new WxMpTemplateData("keyword3", "审核通过"),
                    new WxMpTemplateData("keyword4", approveDTO.getAuditMarkDTO().getAuditClubTime()),
                    new WxMpTemplateData("remark", "欢迎在我们的平台发布社团活动")
            );
        }else if(AuditStatusEnum.AUDIT_FAILED.getCode().equals(auditStatus)){
            //未通过
            data = Arrays.asList(
                    new WxMpTemplateData("first", approveDTO.getAuditMarkDTO().getClubMark()),
                    new WxMpTemplateData("keyword1", approveDTO.getClubName()),
                    new WxMpTemplateData("keyword2", "社团身份认证"),
                    new WxMpTemplateData("keyword3", "审核未通过"),
                    new WxMpTemplateData("keyword4", approveDTO.getAuditMarkDTO().getAuditClubTime()),
                    new WxMpTemplateData("remark", "请检查相关信息是否正确，更正后重新提交，以便工作人员快速审核"));
        }else{
            //审核中
            data = Arrays.asList(
                    new WxMpTemplateData("first", "社团信息正在审核中"),
                    new WxMpTemplateData("keyword1", approveDTO.getClubName()),
                    new WxMpTemplateData("keyword2", "社团身份认证"),
                    new WxMpTemplateData("keyword3", "审核进行中"),
                    new WxMpTemplateData("keyword4", "-"),
                    new WxMpTemplateData("remark", "社团相关信息正在审核中，1-2个工作日内通知审核结果"));
        }
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】发送失败 ,{}", e);
        }
    }

    @Override
    public void positionApproveResultStatus(PositionInfoDTO positionInfoDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        String auditTemplateId = wechatAccountConfig.getTemplateId().get("auditResult");
        templateMessage.setTemplateId(auditTemplateId);
        templateMessage.setToUser(positionInfoDTO.getOpenId());
        List<WxMpTemplateData> data;
        Integer auditStatus = positionInfoDTO.getAuditStatus();
        if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(auditStatus)){
            //通过
            data = Arrays.asList(
                    new WxMpTemplateData("first", "亲，您发布的兼职已经通过审核了哦"),
                    new WxMpTemplateData("keyword1", positionInfoDTO.getCompanyInfo().getCompanyName()),
                    new WxMpTemplateData("keyword2", positionInfoDTO.getPositionName() + "（兼职信息认证）"),
                    new WxMpTemplateData("keyword3", "审核通过"),
                    new WxMpTemplateData("keyword4", positionInfoDTO.getUpdateTime()),
                    new WxMpTemplateData("remark", positionInfoDTO.getAuditRemark())
            );
        }else if(AuditStatusEnum.AUDIT_FAILED.getCode().equals(auditStatus)){
            //未通过
            data = Arrays.asList(
                    new WxMpTemplateData("first", "亲，您发布的兼职审核失败"),
                    new WxMpTemplateData("keyword1", positionInfoDTO.getCompanyInfo().getCompanyName()),
                    new WxMpTemplateData("keyword2", positionInfoDTO.getPositionName() + "兼职信息认证"),
                    new WxMpTemplateData("keyword3", "审核失败"),
                    new WxMpTemplateData("keyword4", positionInfoDTO.getUpdateTime()),
                    new WxMpTemplateData("remark", positionInfoDTO.getAuditRemark())
            );
        }else{
            //审核中
            data = Arrays.asList(
                    new WxMpTemplateData("first", "亲，您发布的兼职正在审核中"),
                    new WxMpTemplateData("keyword1", positionInfoDTO.getCompanyInfo().getCompanyName()),
                    new WxMpTemplateData("keyword2", positionInfoDTO.getPositionName() + "兼职信息认证"),
                    new WxMpTemplateData("keyword3", "审核中"),
                    new WxMpTemplateData("keyword4", "最近两个工作日内会反馈结果"),
                    new WxMpTemplateData("remark", "兼职信息正在审核中，1-2个工作日内通知审核结果")
            );
        }

        templateMessage.setData(data);

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (WxErrorException e) {
            log.error("【微信模板消息】发送失败 ,{}", e);
        }
    }
}