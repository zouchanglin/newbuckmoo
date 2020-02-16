package live.lslm.newbuckmoo.controller.admin;

import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.service.PositionInfoService;
import live.lslm.newbuckmoo.service.WechatPushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
 * 兼职管理控制器
 */
@Slf4j
@Controller
@RequestMapping("/admin/position")
public class PositionManageController {
    @Autowired
    private PositionInfoService positionInfoService;

    @Autowired
    private WechatPushMessageService wechatPushMessageService;

    @GetMapping("list")
    public ModelAndView positionList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     Map<String, Object> map){
        PageRequest pageRequest = PageRequest.of(page-1, size);
        Page<PositionInfoDTO> positionPage = positionInfoService.getAllByAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageRequest);

        map.put("positionPage", positionPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("position/position-list");
    }

    /**
     * 未审核/审核失败列表
     */
    @GetMapping("audit-list")
    public ModelAndView auditList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                  @RequestParam(value = "size", defaultValue = "10") Integer size,
                                  Map<String, Object> map){
        PageRequest pageRequest = PageRequest.of(page-1, size);
        Page<PositionInfoDTO> positionPage = positionInfoService.getAllByNotAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageRequest);

        map.put("positionPage", positionPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("position/approve-list");
    }

    @PostMapping("pass")
    //@CacheEvict(cacheNames = "positionDTOPage", allEntries = true)
    public ModelAndView auditPass(String positionId,
                                  String auditRemark,
                                  Map<String, Object> map){
        try{
            PositionInfoDTO positionInfoDTO = positionInfoService.changeAuditStatus(positionId, AuditStatusEnum.AUDIT_SUCCESS, auditRemark);
            wechatPushMessageService.positionApproveResultStatus(positionInfoDTO);

            map.put("msg", "审核成功");
            map.put("url", "admin/position/audit-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【兼职信息审核】参数错误，positionId={}，auditRemark={}", positionId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/position/audit-list");
            return new ModelAndView("common/error");
        }
    }

    @PostMapping("refer")
    public ModelAndView auditRefer(String positionId,
                                   String auditRemark,
                                   Map<String, Object> map){
        try{
            PositionInfoDTO positionInfoDTO = positionInfoService.changeAuditStatus(positionId, AuditStatusEnum.AUDIT_FAILED, auditRemark);
            wechatPushMessageService.positionApproveResultStatus(positionInfoDTO);

            map.put("msg", "审核成功");
            map.put("url", "admin/position/audit-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【兼职信息审核】参数错误，positionId={}，auditRemark={}", positionId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/position/audit-list");
            return new ModelAndView("common/error");
        }
    }

    /**
     * 获取订单详情页
     */
    @GetMapping("detail")
    public ModelAndView positionDetail(@RequestParam("positionId") String positionId,
                                       Map<String, Object> map){
        PositionInfoDTO positionInfoDTO = positionInfoService.getPositionById(positionId);
        map.put("positionInfo", positionInfoDTO);
        return new ModelAndView("position/detail", map);
    }
}
