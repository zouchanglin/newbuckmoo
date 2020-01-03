package live.lslm.newbuckmoo.controller;

import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.StudentAttestationForm;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 学生认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/user-basic/student-attestation")
public class StudentAttestationController {

    @PostMapping
    public ResultVO attestation(@Valid StudentAttestationForm studentAttestationForm,
                                BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确, orderForm={}", studentAttestationForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info("[StudentAttestationController] studentAttestationForm={}", studentAttestationForm);
        //TODO 剩下的业务逻辑
        return ResultVOUtil.success();
    }
}