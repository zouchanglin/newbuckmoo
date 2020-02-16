package live.lslm.newbuckmoo.controller.total;

import live.lslm.newbuckmoo.service.PayNotifyCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/all-pay/notify")
public class PayNotifyCallbackController {
    @Autowired
    private PayNotifyCallback payNotifyCallback;

    @PostMapping
    public String payNotify(@RequestBody String notifyData){
        log.info("【支付完成统一反馈】{}", notifyData);
        payNotifyCallback.payNotify(notifyData);
        //处理结果返回给微信
        return "pay/success";
    }
}