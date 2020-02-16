package live.lslm.newbuckmoo.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import live.lslm.newbuckmoo.entity.GeneralOrder;
import live.lslm.newbuckmoo.enums.OrderTypeEnum;
import live.lslm.newbuckmoo.enums.PayStatusEnum;
import live.lslm.newbuckmoo.repository.BuyGradeOrderRepository;
import live.lslm.newbuckmoo.repository.GeneralOrderRepository;
import live.lslm.newbuckmoo.service.UserPayService;
import live.lslm.newbuckmoo.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserPayServiceImpl implements UserPayService {
    @Autowired
    private GeneralOrderRepository generalOrderRepository;

    @Autowired
    private BuyGradeOrderRepository buyGradeOrderRepository;

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private RedisTemplate<Object, PayResponse> payResponseRedisTemplate;

    @Override
    public PayResponse userPayNotFinishOrder(String orderId) {
        PayResponse payResponse = payResponseRedisTemplate.opsForValue().get(orderId);
        if(payResponse == null){
            //清除未支付订单，因为订单过期了
            generalOrderRepository.deleteById(orderId);
            //同样的子订单也必须清除
            buyGradeOrderRepository.deleteById(orderId);
        }
        return payResponse;
    }

    @Override
    public Boolean checkOderFinish(String orderId) {
        GeneralOrder generalOrder = generalOrderRepository.getOne(orderId);
        return PayStatusEnum.FINISH_PAY.getCode().equals(generalOrder.getOrderPayStatus());
    }

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