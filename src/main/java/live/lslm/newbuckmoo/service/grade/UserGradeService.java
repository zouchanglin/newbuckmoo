package live.lslm.newbuckmoo.service.grade;

import live.lslm.newbuckmoo.entity.GeneralOrder;
import live.lslm.newbuckmoo.form.UserBuyGradeForm;

public interface UserGradeService {
    /**
     * 注册新用户：初始化积分
     * @param openId openId
     */
    void registerNewUserInitGrade(String openId);

    /**
     * 注册新用户：奖励被推荐人
     * @param openId openId
     */
    void registerNewUserRewardGrade(String openId);

    /**
     * 购买积分生成通用订单
     * @param userBuyGradeForm 用户购买积分表单
     * @return 通用订单信息
     */
    GeneralOrder createBuyGradeOrder(UserBuyGradeForm userBuyGradeForm);

    /**
     * 订单完结并更新用户积分
     * @param generalOrder 通用订单对象
     */
    void finishOrderAndUpdateUserGrade(GeneralOrder generalOrder, String notifyData);
}