package live.lslm.newbuckmoo.controller.admin;


import live.lslm.newbuckmoo.dto.ClubApproveDTO;
import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.service.SchoolClubInfoService;
import live.lslm.newbuckmoo.service.StudentsInfoService;
import live.lslm.newbuckmoo.service.WechatPushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 学生管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/admin/club")
public class ClubManageController {
    @Autowired
    private WechatPushMessageService wechatPushMessageService;

    @Autowired
    private SchoolClubInfoService schoolClubInfoService;
    /**
     * 获取社团信息详情页
     */
    @GetMapping("detail")
    public ModelAndView positionDetail(@RequestParam("openId") String openId,
                                       Map<String, Object> map){

        ClubApproveDTO clubApproveDTO = schoolClubInfoService.getClubInfoByOpenId(openId);
        map.put("clubApproveDTO", clubApproveDTO);
        return new ModelAndView("club/detail", map);
    }

    @PostMapping("pass")
    public ModelAndView auditPass(String openId,
                                  String auditRemark,
                                  Map<String, Object> map){
        try{
            ClubApproveDTO clubApproveDTO = schoolClubInfoService.changeClubApprove(openId, AuditStatusEnum.AUDIT_SUCCESS, auditRemark);
            wechatPushMessageService.clubApproveResultStatus(clubApproveDTO);
            map.put("msg", "审核成功");
            map.put("url", "admin/approve/club-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【学生信息审核】参数错误，openId={}，auditRemark={}", openId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/approve/club-list");
            return new ModelAndView("common/error");
        }
    }

    @PostMapping("refer")
    public ModelAndView auditRefer(String openId,
                                   String auditRemark,
                                   Map<String, Object> map){
        try{
            ClubApproveDTO clubApproveDTO = schoolClubInfoService.changeClubApprove(openId, AuditStatusEnum.AUDIT_FAILED, auditRemark);
            wechatPushMessageService.clubApproveResultStatus(clubApproveDTO);
            map.put("msg", "审核成功");
            map.put("url", "admin/approve/club-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【学生信息审核】参数错误，openId={}，auditRemark={}", openId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/approve/club-list");
            return new ModelAndView("common/error");
        }
    }
}