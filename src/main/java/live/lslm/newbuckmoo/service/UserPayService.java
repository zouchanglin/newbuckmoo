package live.lslm.newbuckmoo.service;

import com.lly835.bestpay.model.PayResponse;
import live.lslm.newbuckmoo.entity.GeneralOrder;

public interface UserPayService {
    PayResponse userBuyGradePay(GeneralOrder generalOrder);
}
