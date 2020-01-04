package live.lslm.newbuckmoo.controller.admin;

import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.entity.StudentInfo;
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
    public String studentApprovePass(){
        return null;
    }


    @GetMapping("student-list")
    public String studentApproveList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     Map<String, Object> map){
        PageRequest request = PageRequest.of(page-1 ,size);
        Page<StudentApproveDTO> studentInfoPage = studentsInfoService.getApproveList(request);
        map.put("studentApprovePage", studentInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return "student/approve-list";
    }
}
