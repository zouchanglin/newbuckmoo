package live.lslm.newbuckmoo.controller.admin;


import live.lslm.newbuckmoo.dto.ClubApproveDTO;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.service.SchoolClubInfoService;
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
     * 审核时社团信息详情页
     */
    @GetMapping("detail")
    public ModelAndView positionDetail(@RequestParam("openId") String openId,
                                       Map<String, Object> map){

        ClubApproveDTO clubApproveDTO = schoolClubInfoService.getClubInfoByOpenId(openId);
        map.put("clubApproveDTO", clubApproveDTO);
        return new ModelAndView("club/approve-detail", map);
    }

    /**
     * 社团信息详情页
     */
    @GetMapping("show-detail")
    public ModelAndView showPositionDetail(@RequestParam("openId") String openId,
                                       Map<String, Object> map){

        ClubApproveDTO clubApproveDTO = schoolClubInfoService.getClubInfoByOpenId(openId);
        map.put("clubApproveDTO", clubApproveDTO);
        return new ModelAndView("club/show-detail", map);
    }

    /**
     * 社团待审核列表
     */
    @GetMapping("approve-list")
    public ModelAndView clubApproveList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                                        Map<String, Object> map){
        PageRequest request = PageRequest.of(page-1 ,size);
        Page<ClubApproveDTO> clubApproveDTOPage = schoolClubInfoService.getApproveList(request);
        map.put("clubApprovePage", clubApproveDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("club/approve-list", map);
    }

    /**
     * 社团信息列表
     */
    @GetMapping("club-list")
    public ModelAndView clubInfoList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                                        Map<String, Object> map){
        PageRequest request = PageRequest.of(page-1 ,size);
        Page<ClubApproveDTO> clubApproveDTOPage = schoolClubInfoService.getClubList(request);
        map.put("clubPage", clubApproveDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("club/club-list", map);
    }

    /**
     * 社团通过审核
     */
    @PostMapping("pass")
    public ModelAndView auditPass(String openId, String auditRemark, Map<String, Object> map){
        try{
            ClubApproveDTO clubApproveDTO = schoolClubInfoService.changeClubApprove(openId, AuditStatusEnum.AUDIT_SUCCESS, auditRemark);
            wechatPushMessageService.clubApproveResultStatus(clubApproveDTO);
            map.put("msg", "审核成功");
            map.put("url", "admin/club/approve-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【社团信息审核】参数错误，openId={}，auditRemark={}", openId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/club/approve-list");
            return new ModelAndView("common/error");
        }
    }

    /**
     * 社团拒绝通过审核
     */
    @PostMapping("refer")
    public ModelAndView auditRefer(String openId, String auditRemark, Map<String, Object> map){
        try{
            ClubApproveDTO clubApproveDTO = schoolClubInfoService.changeClubApprove(openId, AuditStatusEnum.AUDIT_FAILED, auditRemark);
            wechatPushMessageService.clubApproveResultStatus(clubApproveDTO);
            map.put("msg", "审核成功");
            map.put("url", "admin/club/approve-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【社团信息审核】参数错误，openId={}，auditRemark={}", openId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/club/approve-list");
            return new ModelAndView("common/error");
        }
    }
}