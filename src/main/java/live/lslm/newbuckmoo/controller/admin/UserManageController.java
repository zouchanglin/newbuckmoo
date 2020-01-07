package live.lslm.newbuckmoo.controller.admin;

import live.lslm.newbuckmoo.dto.ClubApproveDTO;
import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.service.CompanyInfoService;
import live.lslm.newbuckmoo.service.SchoolClubInfoService;
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
@RequestMapping("/admin/manage")
public class UserManageController {
    @Autowired
    private StudentsInfoService studentsInfoService;
    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private SchoolClubInfoService schoolClubInfoService;
    @GetMapping("student-list")
    public ModelAndView studentApproveList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     Map<String, Object> map){
        PageRequest request = PageRequest.of(page-1 ,size);
        Page<StudentApproveDTO> studentInfoPage = studentsInfoService.getStudentList(request);
        map.put("studentPage", studentInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("student/student-list", map);
    }

    @GetMapping("company-list")
    public ModelAndView companyApproveList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                                           Map<String, Object> map){
        PageRequest request = PageRequest.of(page-1 ,size);
        Page<CompanyApproveDTO> companyApproveDTOPage = companyInfoService.getCompanyList(request);
        map.put("companyPage", companyApproveDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("company/company-list", map);
    }

    @GetMapping("club-list")
    public ModelAndView clubApproveList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                                           Map<String, Object> map){
        PageRequest request = PageRequest.of(page-1 ,size);
        Page<ClubApproveDTO> clubApproveDTOPage = schoolClubInfoService.getClubList(request);
        map.put("clubPage", clubApproveDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("club/club-list", map);
    }
}