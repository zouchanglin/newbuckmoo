package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.entity.GeneralOrder;
import live.lslm.newbuckmoo.enums.OrderTypeEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.repository.GeneralOrderRepository;
import live.lslm.newbuckmoo.service.PayNotifyCallback;
import live.lslm.newbuckmoo.service.grade.ClubGradService;
import live.lslm.newbuckmoo.service.grade.CompanyGradeService;
import live.lslm.newbuckmoo.service.grade.StudentGradeService;
import live.lslm.newbuckmoo.utils.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class PayNotifyCallbackImpl implements PayNotifyCallback {
    @Autowired
    private GeneralOrderRepository generalOrderRepository;

    @Autowired
    private StudentGradeService studentGradeService;

    @Autowired
    private CompanyGradeService companyGradeService;

    @Autowired
    private ClubGradService clubGradService;

    @Override
    public void payNotify(String notifyData) {
        String orderId;
        Document document= null;
        try {
            document = DocumentHelper.parseText(notifyData);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //拿到订单ID
        orderId = document.selectSingleNode("//out_trade_no").getText();

        //先查订单
        Optional<GeneralOrder> orderOptional = generalOrderRepository.findById(orderId);

        if(orderOptional.isPresent()){
            GeneralOrder generalOrder = orderOptional.get();
            OrderTypeEnum orderTypeEnum = EnumUtil.getByCode(generalOrder.getOrderType(), OrderTypeEnum.class);
            switch (orderTypeEnum){
                case STUDENT_BUY_GRADE:
                    studentGradeService.finishOrderAndUpdateUserGrade(generalOrder, notifyData);
                    break;
                case COMPANY_BUY_GRADE:
                    companyGradeService.finishOrderAndUpdateUserGrade(generalOrder, notifyData);
                    break;
                case CLUB_BUY_GRADE:
                    clubGradService.finishOrderAndUpdateUserGrade(generalOrder, notifyData);
                    break;
                case OTHER_BUY_GRADE:
                    break;
                default:
                    log.info("【订单支付成功的回调】 其他情况");
            }
        }else {
            throw new BuckmooException(ResultEnum.ORDER_ID_ERROR);
        }
    }
}