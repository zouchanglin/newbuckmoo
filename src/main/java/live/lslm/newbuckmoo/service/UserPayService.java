package live.lslm.newbuckmoo.service;

import com.lly835.bestpay.model.PayResponse;
import live.lslm.newbuckmoo.entity.GeneralOrder;

public interface UserPayService {
    /* 用户购买积分 */
    PayResponse userBuyGradePay(GeneralOrder generalOrder);

    /* 检查订单是否支付完成 */
    Boolean checkOderFinish(String orderId);

    /* 支付未完成的订单 */
    PayResponse userPayNotFinishOrder(String orderId);
}
