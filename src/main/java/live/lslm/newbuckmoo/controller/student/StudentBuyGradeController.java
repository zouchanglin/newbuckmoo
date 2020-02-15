package live.lslm.newbuckmoo.controller.student;

import com.lly835.bestpay.model.PayResponse;
import live.lslm.newbuckmoo.entity.GeneralOrder;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.UserBuyGradeForm;
import live.lslm.newbuckmoo.service.UserPayService;
import live.lslm.newbuckmoo.service.grade.StudentGradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/student/buy")
public class StudentBuyGradeController {
    @Autowired
    private StudentGradeService studentGradeService;

    @Autowired
    private UserPayService userPayService;

    @PostMapping("grade")
    public ModelAndView studentBuyGrade(@RequestBody @Valid UserBuyGradeForm userBuyGradeForm,
                                        BindingResult bindingResult,
                                        Map<String, Object> map){
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, {}", userBuyGradeForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        //创建订单
        GeneralOrder generalOrder = studentGradeService.createBuyGradeOrder(userBuyGradeForm);

        //发起支付
        PayResponse payResponse = userPayService.userBuyGradePay(generalOrder);
        map.put("payResponse", payResponse);
        map.put("returnUrl", userBuyGradeForm.getReturnUrl());
        return new ModelAndView("pay/create");
    }

    //TODO 测试支付
    @GetMapping("grade-test")
    public ModelAndView testStudentBuyGrade(String openId, Integer typeId, String returnUrl, Map<String, Object> map){

        UserBuyGradeForm userBuyGradeForm = new UserBuyGradeForm();
        userBuyGradeForm.setOpenId(openId);
        userBuyGradeForm.setGradeComboId(typeId);
        userBuyGradeForm.setReturnUrl(returnUrl);

        //创建订单
        GeneralOrder generalOrder = studentGradeService.createBuyGradeOrder(userBuyGradeForm);

        //发起支付
        PayResponse payResponse = userPayService.userBuyGradePay(generalOrder);
        map.put("payResponse", payResponse);
        map.put("returnUrl", userBuyGradeForm.getReturnUrl());
        return new ModelAndView("pay/create");
    }
}