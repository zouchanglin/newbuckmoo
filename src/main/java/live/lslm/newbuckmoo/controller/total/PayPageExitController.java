package live.lslm.newbuckmoo.controller.total;


import live.lslm.newbuckmoo.repository.GeneralOrderRepository;
import live.lslm.newbuckmoo.service.UserPayService;
import live.lslm.newbuckmoo.service.WechatPushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/all-pay/exit")
public class PayPageExitController {
    @Autowired
    private UserPayService userPayService;

    @Autowired
    private GeneralOrderRepository generalOrderRepository;

    @Autowired
    private WechatPushMessageService wechatPushMessageService;

    @GetMapping
    public ModelAndView get(String orderId, String returnUrl){
        log.info("【支付前端完成】orderId {}", orderId);
        //检查是否支付完成
        Boolean checkOderFinish = userPayService.checkOderFinish(orderId);
        if(!checkOderFinish) wechatPushMessageService.userPayNotFinish(generalOrderRepository.getOne(orderId));
        return new ModelAndView(String.format("redirect:%s", returnUrl));
    }
}