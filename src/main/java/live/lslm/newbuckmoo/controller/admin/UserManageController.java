package live.lslm.newbuckmoo.controller.admin;

import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.service.CompanyInfoService;
import live.lslm.newbuckmoo.service.StudentsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/approve")
public class UserManageController {
    @Autowired
    private StudentsInfoService studentsInfoService;
    @Autowired
    private CompanyInfoService companyInfoService;

    @GetMapping("student-pass")
    public ModelAndView studentApprovePass(@RequestParam("openid") String openid,
                                           Map<String, Object> map){
        try{
            studentsInfoService.passStudentApprove(openid);
        }catch (BuckmooException e){
            log.error("[学生信息通过审核]发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/newbuckmoo/admin/approve/student-list");
            return new ModelAndView("common/error");
        }
        map.put("msg", ResultEnum.PASS_AUDIT.getMessage());
        map.put("url", "/newbuckmoo/admin/approve/student-list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("student-rejected")
    public ModelAndView studentApproveRejected(@RequestParam("openid") String openid,
                                           Map<String, Object> map){
        try{
            studentsInfoService.rejectedStudentApprove(openid);
        }catch (BuckmooException e){
            log.error("[学生信息驳回审核]发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/newbuckmoo/admin/approve/student-list");
            return new ModelAndView("common/error");
        }
        map.put("msg", ResultEnum.NOT_PASS_AUDIT.getMessage());
        map.put("url", "/newbuckmoo/admin/approve/student-list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("student-list")
    public ModelAndView studentApproveList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     Map<String, Object> map){
        PageRequest request = PageRequest.of(page-1 ,size);
        Page<StudentApproveDTO> studentInfoPage = studentsInfoService.getApproveList(request);
        map.put("studentApprovePage", studentInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("student/approve-list", map);
    }

    @GetMapping("company-list")
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
}