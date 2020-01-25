package live.lslm.newbuckmoo.controller.admin;

import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.service.PositionInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("list")
    public ModelAndView positionList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     Map<String, Object> map){
        PageRequest pageRequest = PageRequest.of(page-1, size);
        Page<PositionInfoDTO> positionPage = positionInfoService.getAllByAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageRequest);
        log.info("[获取的数据] {}", positionPage.getContent());

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
        log.info("[获取的数据] {}", positionPage.getContent());

        map.put("positionPage", positionPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("position/approve-list");
    }
}
