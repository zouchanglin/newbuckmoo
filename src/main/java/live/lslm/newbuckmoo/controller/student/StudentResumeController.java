package live.lslm.newbuckmoo.controller.student;

import live.lslm.newbuckmoo.dto.StudentResumeDTO;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.StudentResumeForm;
import live.lslm.newbuckmoo.service.StudentResumeService;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.ResultVO;
import live.lslm.newbuckmoo.vo.StudentResumeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 学生简历
 */
@Slf4j
@RestController
@RequestMapping("/student/resume")
public class StudentResumeController {
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

    @PostMapping("download")
    public ResultVO getMyResume(@RequestBody Map<String, Object> map){
        String openId = (String) map.get("openId");
        if(StringUtils.isEmpty(openId)) throw new BuckmooException(ResultEnum.PARAM_ERROR);
        StudentResumeVO resumeVO = studentResumeService.getOneResumeByOpenId(openId);
        return ResultVOUtil.success(resumeVO);
    }
}