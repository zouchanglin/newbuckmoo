package live.lslm.newbuckmoo.controller.student;

import live.lslm.newbuckmoo.dto.StudentResumeDTO;
import live.lslm.newbuckmoo.form.StudentResumeForm;
import live.lslm.newbuckmoo.service.StudentResumeService;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 学生简历
 */
@RestController
@RequestMapping("/student/resume")
public class ResumeController {
    @Autowired
    private StudentResumeService studentResumeService;

    /**
     * 学生上传简历
     */
    @PostMapping("upload")
    public ResultVO upLoadResume(@RequestBody @Valid StudentResumeForm studentResumeForm){
        StudentResumeDTO orUpdateResume = studentResumeService.createOrUpdateResume(studentResumeForm);
        return ResultVOUtil.success();
    }
}