package live.lslm.newbuckmoo.controller.admin;

import live.lslm.newbuckmoo.dto.ClubApproveDTO;
import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.service.CompanyInfoService;
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

@Slf4j
@Controller
@RequestMapping("/admin/company")
public class CompanyController {
    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private WechatPushMessageService wechatPushMessageService;
    /**
     * 获取社团信息详情页
     */
    @GetMapping("detail")
    public ModelAndView positionDetail(@RequestParam("openId") String openId,
                                       Map<String, Object> map){
        CompanyApproveDTO companyApproveDTO = companyInfoService.getCompanyByOpenId(openId);
        map.put("companyApproveDTO", companyApproveDTO);
        return new ModelAndView("company/detail", map);
    }

    @PostMapping("pass")
    public ModelAndView auditPass(String openId,
                                  String auditRemark,
                                  Map<String, Object> map){
        try{
            CompanyApproveDTO companyApproveDTO = companyInfoService.changeCompanyApprove(openId, AuditStatusEnum.AUDIT_SUCCESS, auditRemark);
            wechatPushMessageService.companyApproveResultStatus(companyApproveDTO);
            map.put("msg", "审核成功");
            map.put("url", "admin/approve/company-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【企业信息审核】参数错误，openId={}，auditRemark={}", openId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/approve/company-list");
            return new ModelAndView("common/error");
        }
    }

    @PostMapping("refer")
    public ModelAndView auditRefer(String openId,
                                   String auditRemark,
                                   Map<String, Object> map){
        try{
            CompanyApproveDTO companyApproveDTO = companyInfoService.changeCompanyApprove(openId, AuditStatusEnum.AUDIT_FAILED, auditRemark);
            wechatPushMessageService.companyApproveResultStatus(companyApproveDTO);
            map.put("msg", "审核成功");
            map.put("url", "admin/approve/company-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【企业信息审核】参数错误，openId={}，auditRemark={}", openId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/approve/company-list");
            return new ModelAndView("common/error");
        }
    }
}