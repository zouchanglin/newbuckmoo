package live.lslm.newbuckmoo.controller.student;

import live.lslm.newbuckmoo.dto.StudentResumeDTO;
import live.lslm.newbuckmoo.form.StudentResumeForm;
import live.lslm.newbuckmoo.service.StudentResumeService;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 学生简历
 */
@Slf4j
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
        log.info("[简历保存结果] {}", orUpdateResume);
        return ResultVOUtil.success();
    }

    @GetMapping("download")
    public ResultVO getMyResume(){

        return null;
    }
}