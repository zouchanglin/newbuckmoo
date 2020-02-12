package live.lslm.newbuckmoo.controller.admin;


import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.service.StudentsInfoService;
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
@RequestMapping("/admin/student")
public class StudentManageController {
    @Autowired
    private StudentsInfoService studentsInfoService;
    @Autowired
    private WechatPushMessageService wechatPushMessageService;
    /**
     * 审核时获取学生信息详情页
     */
    @GetMapping("detail")
    public ModelAndView positionDetail(@RequestParam("openId") String openId,
                                       Map<String, Object> map){
        StudentApproveDTO studentApproveDTO = studentsInfoService.getStudentInfoByOpenId(openId);
        map.put("studentDTO", studentApproveDTO);
        return new ModelAndView("student/approve-detail", map);
    }

    /**
     * 获取学生信息详情页
     */
    @GetMapping("show-detail")
    public ModelAndView showPositionDetail(@RequestParam("openId") String openId,
                                       Map<String, Object> map){
        StudentApproveDTO studentApproveDTO = studentsInfoService.getStudentInfoByOpenId(openId);
        map.put("studentDTO", studentApproveDTO);
        return new ModelAndView("student/show-detail", map);
    }

    @GetMapping("approve-list")
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

    @GetMapping("student-list")
    public ModelAndView studentInfoList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                                           Map<String, Object> map){
        PageRequest request = PageRequest.of(page-1 ,size);
        Page<StudentApproveDTO> studentInfoPage = studentsInfoService.getStudentList(request);
        map.put("studentPage", studentInfoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("student/student-list", map);
    }


    @PostMapping("pass")
    public ModelAndView auditPass(String openId,
                                  String auditRemark,
                                  Map<String, Object> map){
        try{
            StudentApproveDTO studentApproveDTO = studentsInfoService.passStudentApprove(openId, auditRemark);
            wechatPushMessageService.studentApproveResultStatus(studentApproveDTO);
            map.put("msg", "审核成功");
            map.put("url", "admin/student/approve-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【学生信息审核】参数错误，openId={}，auditRemark={}", openId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/student/approve-list");
            return new ModelAndView("common/error");
        }
    }

    @PostMapping("refer")
    public ModelAndView auditRefer(String openId,
                                   String auditRemark,
                                   Map<String, Object> map){
        try{
            StudentApproveDTO studentApproveDTO = studentsInfoService.rejectedStudentApprove(openId, auditRemark);
            wechatPushMessageService.studentApproveResultStatus(studentApproveDTO);

            map.put("msg", "审核完成");
            map.put("url", "admin/student/approve-list");
            return new ModelAndView("common/success");
        }catch (BuckmooException e){
            log.error("【学生信息审核】参数错误，openId={}，auditRemark={}", openId, auditRemark);
            map.put("msg", e.getMessage());
            map.put("url", "admin/student/approve-list");
            return new ModelAndView("common/error");
        }
    }
}