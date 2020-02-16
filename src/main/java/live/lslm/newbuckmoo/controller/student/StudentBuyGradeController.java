package live.lslm.newbuckmoo.controller.student;

import com.lly835.bestpay.model.PayResponse;
import live.lslm.newbuckmoo.config.ProjectUrlConfig;
import live.lslm.newbuckmoo.entity.GeneralOrder;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.UserBuyGradeForm;
import live.lslm.newbuckmoo.service.UserPayService;
import live.lslm.newbuckmoo.service.grade.StudentGradeService;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.BuyGradeOrderVO;
import live.lslm.newbuckmoo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 学生购买积分的Controller
 */
@Slf4j
@Controller
@RequestMapping("/student/buy")
public class StudentBuyGradeController {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private StudentGradeService studentGradeService;

    @Autowired
    private UserPayService userPayService;

    @Autowired
    private RedisTemplate<Object, PayResponse> payResponseRedisTemplate;

    /* 购买积分套餐 */
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

        //支付对象设置进Redis
        payResponseRedisTemplate.opsForValue().set(generalOrder.getOrderId(), payResponse, 30, TimeUnit.MINUTES);

        map.put("payResponse", payResponse);
        String url = projectUrlConfig.newbuckmoo;
        String format = String.format("%s/all-pay/exit?orderId=%s&returnUrl=%s", url, generalOrder.getOrderId(), userBuyGradeForm.getReturnUrl());
        map.put("returnUrl", format);
        return new ModelAndView("pay/create");
    }

    /* 支付未完成的订单 */
    @PostMapping("not-finish")
    public ModelAndView studentPayNotFinishOrder(@RequestBody Map<String, Object> map){
        String orderId = (String) map.get("orderId");
        String returnUrl = (String) map.get("returnUrl");

        PayResponse payResponse = userPayService.userPayNotFinishOrder(orderId);
        if(payResponse == null) return new ModelAndView("pay/overdue");

        map.put("payResponse", payResponse);
        String url = projectUrlConfig.newbuckmoo;
        String format = String.format("%s/all-pay/exit?orderId=%s&returnUrl=%s", url, orderId, returnUrl);
        map.put("returnUrl", format);
        return new ModelAndView("pay/create");
    }

    /* 获取用户全部购买积分订单 */
    @ResponseBody
    @PostMapping("order-list")
    public ResultVO getMyBuyGradeOrder(@RequestBody Map<String, Object> map){
        String openId = (String)map.get("openId");
        List<BuyGradeOrderVO> allBuyGradeOrder = studentGradeService.getAllBuyGradeOrder(openId);
        return ResultVOUtil.success(allBuyGradeOrder);
    }

    //TODO 支付测试接口
    @GetMapping("not-finish-test")
    public ModelAndView studentPayNotFinishOrderTest(String orderId, String returnUrl, Map<String, Object> map){
        PayResponse payResponse = userPayService.userPayNotFinishOrder(orderId);
        if(payResponse == null) return new ModelAndView("pay/overdue");

        map.put("payResponse", payResponse);
        String url = projectUrlConfig.newbuckmoo;
        String format = String.format("%s/all-pay/exit?orderId=%s&returnUrl=%s", url, orderId, returnUrl);
        map.put("returnUrl", format);
        return new ModelAndView("pay/create");
    }

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

        //支付对象设置进Redis
        payResponseRedisTemplate.opsForValue().set(generalOrder.getOrderId(), payResponse, 30, TimeUnit.MINUTES);

        map.put("payResponse", payResponse);
        String url = projectUrlConfig.newbuckmoo;
        String format = String.format("%s/all-pay/exit?orderId=%s&returnUrl=%s", url, generalOrder.getOrderId(), userBuyGradeForm.getReturnUrl());
        map.put("returnUrl", format);
        return new ModelAndView("pay/create");
    }
}