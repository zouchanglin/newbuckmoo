package live.lslm.newbuckmoo.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import live.lslm.newbuckmoo.entity.GeneralOrder;
import live.lslm.newbuckmoo.enums.OrderTypeEnum;
import live.lslm.newbuckmoo.service.UserPayService;
import live.lslm.newbuckmoo.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserPayServiceImpl implements UserPayService {
    @Autowired
    private BestPayServiceImpl bestPayService;

    @Override
    public PayResponse userBuyGradePay(GeneralOrder generalOrder) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(generalOrder.getOrderOpenId());
        payRequest.setOrderId(generalOrder.getOrderId());
        payRequest.setOrderAmount(generalOrder.getOrderMoney().doubleValue());
        payRequest.setOrderName(EnumUtil.getByCode(generalOrder.getOrderType(), OrderTypeEnum.class).getMessage());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        return bestPayService.pay(payRequest);
    }
}