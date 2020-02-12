package live.lslm.newbuckmoo.controller.admin;

import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.service.CompanyInfoService;
import live.lslm.newbuckmoo.service.WechatPushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
     * 获取信息详情页->审核
     */
    @GetMapping("detail")
    public ModelAndView positionDetail(@RequestParam("openId") String openId,
                                       Map<String, Object> map){
        CompanyApproveDTO companyApproveDTO = companyInfoService.getCompanyByOpenId(openId);
        map.put("companyApproveDTO", companyApproveDTO);
        return new ModelAndView("company/approve-detail", map);
    }

    /**
     * 获取信息详情页->查看（已经审核完毕了）
     */
    @GetMapping("show-detail")
    public ModelAndView showPositionDetail(@RequestParam("openId") String openId,
                                       Map<String, Object> map){
        CompanyApproveDTO companyApproveDTO = companyInfoService.getCompanyByOpenId(openId);
        map.put("companyApproveDTO", companyApproveDTO);
        return new ModelAndView("company/show-detail", map);
    }

    @GetMapping("approve-list")
    public ModelAndView companyApproveList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                                           Map<String, Object> map){
        PageRequest request = PageRequest.of(page-1 ,size);
        Page<CompanyApproveDTO> companyApproveDTOPage = companyInfoService.getApproveList(request);
        map.put("companyApprovePage", companyApproveDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("company/approve-list", map);
    }

    @GetMapping("company-list")
    public ModelAndView companyInfoList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                                           Map<String, Object> map){
        PageRequest request = PageRequest.of(page-1 ,size);
        Page<CompanyApproveDTO> companyApproveDTOPage = companyInfoService.getCompanyList(request);
        map.put("companyPage", companyApproveDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("company/company-list", map);
    }

    @PostMapping("pass")
    public ModelAndView auditPass(String openId,
                                  String auditRemark,
                                  Map<String, Object> map){
        try{
            CompanyApproveDTO companyApproveDTO = companyInfoService.changeCompanyApprove(openId, AuditStatusEnum.AUDIT_SUCCESS, auditRemark);
            wechatPushMessageService.companyApproveResultStatus(companyApproveDTO);
            map.put("msg", "审核成功");
            map.put("url", "admin/company/approve-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【企业信息审核】参数错误，openId={}，auditRemark={}", openId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/company/approve-list");
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
            map.put("url", "admin/company/approve-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【企业信息审核】参数错误，openId={}，auditRemark={}", openId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/company/approve-list");
            return new ModelAndView("common/error");
        }
    }
}