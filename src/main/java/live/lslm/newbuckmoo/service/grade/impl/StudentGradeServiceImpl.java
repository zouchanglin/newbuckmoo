package live.lslm.newbuckmoo.service.grade.impl;

import com.google.common.collect.Lists;
import com.lly835.bestpay.model.PayResponse;
import live.lslm.newbuckmoo.entity.*;
import live.lslm.newbuckmoo.enums.*;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.UserBuyGradeForm;
import live.lslm.newbuckmoo.repository.*;
import live.lslm.newbuckmoo.service.StudentsInfoService;
import live.lslm.newbuckmoo.service.WechatPushMessageService;
import live.lslm.newbuckmoo.service.admin.SettingEnum;
import live.lslm.newbuckmoo.service.admin.SettingService;
import live.lslm.newbuckmoo.service.grade.GradeComboService;
import live.lslm.newbuckmoo.service.grade.StudentGradeService;
import live.lslm.newbuckmoo.utils.ConstUtilPoll;
import live.lslm.newbuckmoo.utils.EnumUtil;
import live.lslm.newbuckmoo.utils.KeyUtil;
import live.lslm.newbuckmoo.vo.BuyGradeOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class StudentGradeServiceImpl implements StudentGradeService {
    @Autowired
    private StudentInfoRepository studentInfoRepository;

    @Autowired
    private StudentsInfoService studentsInfoService;

    @Autowired
    private UserGradeRepository userGradeRepository;

    @Autowired
    private RecommendSignRepository recommendSignRepository;

    @Autowired
    private SettingService settingService;

    @Autowired
    private GradeComboService gradeComboService;

    @Autowired
    private GeneralOrderRepository generalOrderRepository;

    @Autowired
    private BuyGradeOrderRepository buyGradeOrderRepository;

    @Autowired
    private WechatPushMessageService wechatPushMessageService;

    @Autowired
    private RedisTemplate<Object, PayResponse> payResponseRedisTemplate;


    @Override
    public List<BuyGradeOrderVO> getAllBuyGradeOrder(String openId) {
        List<GeneralOrder> generalOrderList = generalOrderRepository.findAllByOrderOpenIdOrderByCreateTime(openId);
        List<BuyGradeOrderVO> buyGradeOrderVOList = Lists.newArrayListWithCapacity(generalOrderList.size());
        BuyGradeOrderVO buyGradeOrderVO;
        for(GeneralOrder generalOrder: generalOrderList){
            buyGradeOrderVO = new BuyGradeOrderVO();
            BuyGradeOrder gradeOrder = buyGradeOrderRepository.getOne(generalOrder.getOrderId());
            BeanUtils.copyProperties(generalOrder, buyGradeOrderVO);
            buyGradeOrderVO.setCreateTimeStr(ConstUtilPoll.dateFormat.format(new Date(generalOrder.getCreateTime())));
            buyGradeOrderVO.setUpdateTimeStr(ConstUtilPoll.dateFormat.format(new Date(generalOrder.getUpdateTime())));
            GradeCombo gradeCombo = gradeComboService.getOneComboById(gradeOrder.getGradeComboId());
            buyGradeOrderVO.setGradeName(gradeCombo.getGradeName());
            buyGradeOrderVO.setGradeNum(gradeCombo.getGradeNum());
            buyGradeOrderVO.setOrderMoneyStr(generalOrder.getOrderMoney().toString());
            buyGradeOrderVO.setOrderPayStatusStr(EnumUtil.getByCode(generalOrder.getOrderPayStatus(), PayStatusEnum.class).getMessage());
            buyGradeOrderVO.setOrderTypeStr(EnumUtil.getByCode(generalOrder.getOrderType(), OrderTypeEnum.class).getMessage());
            buyGradeOrderVOList.add(buyGradeOrderVO);
        }
        return buyGradeOrderVOList;
    }

    @Override
    @Transactional
    public void finishOrderAndUpdateUserGrade(GeneralOrder generalOrder, String notifyData) {
        Document document= null;
        try {
            document = DocumentHelper.parseText(notifyData);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        String total_fee = document.selectSingleNode("//total_fee").getText();
        String openid = document.selectSingleNode("//openid").getText();
        String time_end = document.selectSingleNode("//time_end").getText();

        //更新订单
        BigDecimal returnOrderMoney = new BigDecimal(Integer.parseInt(total_fee)).divide(new BigDecimal(100), 2, RoundingMode.DOWN);
        BigDecimal orderMoney = generalOrder.getOrderMoney();
        if(returnOrderMoney.equals(orderMoney)){
            if(openid.equals(generalOrder.getOrderOpenId())){
                try {
                    generalOrder.setUpdateTime(ConstUtilPoll.wechatOrderDateFormat.parse(time_end).getTime());
                } catch (ParseException e) {
                    log.error("【字符串日期解析出错】");
                    e.printStackTrace();
                }
            }else {
                throw new BuckmooException(ResultEnum.ORDER_OPENID_ERROR);
            }
        }else {
            throw new BuckmooException(ResultEnum.ORDER_MONEY_ERROR);
        }
        generalOrder.setOrderPayStatus(PayStatusEnum.FINISH_PAY.getCode());
        generalOrderRepository.save(generalOrder);

        //更新积分
        //查询子订单
        Optional<BuyGradeOrder> buyGradeOrderOpt = buyGradeOrderRepository.findById(generalOrder.getOrderId());
        if(!buyGradeOrderOpt.isPresent()) throw new BuckmooException(ResultEnum.ORDER_ID_ERROR);

        //查询套餐
        GradeCombo gradeCombo = gradeComboService.getOneComboById(buyGradeOrderOpt.get().getGradeComboId());

        //查询用户积分表
        Optional<UserGrade> userGradeOptional = userGradeRepository.findById(openid);
        if(userGradeOptional.isPresent()){
            UserGrade userGrade = userGradeOptional.get();
            //Add User Grade
            userGrade.setStudentGrade(userGrade.getStudentGrade() + gradeCombo.getGradeNum());
            UserGrade savedUserGrade = userGradeRepository.save(userGrade);
            log.info("【学生用户积分充值】更新结果{}", savedUserGrade);
        }else {
            log.error("【学生用户积分充值】暂无该用户的积分表");
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
        //删除Redis的支付对象
        payResponseRedisTemplate.opsForValue().set(generalOrder.getOrderId(), null, 2, TimeUnit.SECONDS);
        //微信模板消息推送
        wechatPushMessageService.userPayGradeSuccess(generalOrder);
    }

    @Override
    public GeneralOrder createBuyGradeOrder(UserBuyGradeForm userBuyGradeForm) {
        String openId = userBuyGradeForm.getOpenId();
        if(!studentsInfoService.isAuditPassUser(openId)){
            log.error("【学生用户积分充值】非学生用户/学生审核未通过");
            throw new BuckmooException(ResultEnum.PERMISSION_ERROR);
        }
        GradeCombo gradeCombo = gradeComboService.getOneComboById(userBuyGradeForm.getGradeComboId());
        GeneralOrder order = new GeneralOrder();
        String orderId = KeyUtil.genUniqueKey();
        order.setOrderId(orderId);
        order.setOrderMoney(gradeCombo.getGradeMoney());
        order.setOrderName(String.format("购买: %s学生积分套餐", gradeCombo.getGradeName()));
        order.setOrderOpenId(userBuyGradeForm.getOpenId());
        order.setOrderPayStatus(PayStatusEnum.WILL_PAY.getCode());
        order.setOrderType(OrderTypeEnum.STUDENT_BUY_GRADE.getCode());

        order.setCreateTime(System.currentTimeMillis());
        order.setUpdateTime(System.currentTimeMillis());

        GeneralOrder generalOrder = generalOrderRepository.save(order);
        log.info("【学生用户积分充值】 统一订单生成结果 {}", generalOrder);

        //生成购买积分专用订单
        BuyGradeOrder buyGradeOrder = new BuyGradeOrder();
        buyGradeOrder.setOrderId(orderId);
        buyGradeOrder.setOrderOther("");
        buyGradeOrder.setBuyerOpenId(openId);
        buyGradeOrder.setGradeComboId(userBuyGradeForm.getGradeComboId());
        BuyGradeOrder savedBuyGradeOrder = buyGradeOrderRepository.save(buyGradeOrder);
        log.info("【学生用户积分充值】 具体订单生成结果 {}", savedBuyGradeOrder);

        return generalOrder;
    }

    @Override
    public void registerNewUserRewardGrade(String openId) {
        RecommendSign recommendSign = recommendSignRepository.findFirstBySignOpenIdAndRecommendType(openId, RecommendTypeEnum.STUDENT_RECOMMEND.getCode());
        if(recommendSign != null){
            String pushOpenId = recommendSign.getPushOpenId();
            Optional<UserGrade> userGradeOptional = userGradeRepository.findById(pushOpenId);
            if(userGradeOptional.isPresent()){
                UserGrade userGrade = userGradeOptional.get();
                SystemSettings setting = settingService.getOneSetting(SettingEnum.RECOMMEND_STUDENT);
                userGrade.setStudentGrade(userGrade.getStudentGrade() + Integer.parseInt(setting.getSystemValue()));
                userGradeRepository.save(userGrade);
            }else{
                log.error("【奖励积分：推荐学生注册通过】积分表中找不到推荐人");
            }
        }else {
            log.error("【奖励积分：推荐学生注册通过】推荐表中找不到推荐人");
        }
    }

    @Override
    public void registerNewUserInitGrade(String openId) {
        if(StringUtils.isEmpty(openId)) return;
        Optional<StudentInfo> studentInfoOptional = studentInfoRepository.findById(openId);
        if(studentInfoOptional.isPresent()){
            StudentInfo studentInfo = studentInfoOptional.get();
            if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(studentInfo.getAuditStatus())){
                Optional<UserGrade> userGrade = userGradeRepository.findById(openId);
                UserGrade saveGrade = new UserGrade();
                if(userGrade.isPresent()){
                    BeanUtils.copyProperties(userGrade.get(), saveGrade);
                }else{
                    saveGrade.setOpenId(openId);
                    saveGrade.setClubGrade(0);
                    saveGrade.setCompanyGrade(0);
                }
                SystemSettings setting = settingService.getOneSetting(SettingEnum.NEW_STUDENT);
                saveGrade.setStudentGrade(Integer.parseInt(setting.getSystemValue()));
                UserGrade save = userGradeRepository.save(saveGrade);
                log.info("【学生初始化积分】 {}", save);
            }
            log.error("【学生初始化积分】学生审核状态不正确");
        }else{
            log.error("【学生初始化积分】 学生信息表中无信息");
        }
    }
}