package live.lslm.newbuckmoo.controller;

import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.StudentAttestationForm;
import live.lslm.newbuckmoo.service.StudentsInfoService;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 学生认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/user-basic/student-attestation")
public class StudentAttestationController {
    @Autowired
    private StudentsInfoService studentsInfoService;

    /**
     * @deprecated
     * 为了JSON数据格式交互, 建议使用如下接口
     * {@link live.lslm.newbuckmoo.controller.StudentAttestationController#attestation(StudentAttestationForm, BindingResult)}
     * @param studentAttestationForm 学生信息表单
     * @param bindingResult 绑定返回值
     * @return 提交信息处理结果
     */
    @Deprecated
    @GetMapping
    public ResultVO attestationOld(@Valid StudentAttestationForm studentAttestationForm,
                                BindingResult bindingResult, String openid){
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, studentAttestationForm={}", studentAttestationForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info("[StudentAttestationController] studentAttestationForm={}", studentAttestationForm);
        log.info("[StudentAttestationController] openId={}", openid);
        return ResultVOUtil.success();
    }

    @PostMapping(produces = "application/json")
    public ResultVO attestation(@RequestBody @Valid StudentAttestationForm studentAttestationForm,
                        BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, studentAttestationForm={}", studentAttestationForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info("[StudentAttestationController] studentAttestationForm={}", studentAttestationForm);
        //studentsInfoService.createOrUpdateInfo(studentAttestationForm);
        return ResultVOUtil.success(studentAttestationForm);
    }
}