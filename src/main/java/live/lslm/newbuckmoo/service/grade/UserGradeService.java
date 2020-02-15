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


    GeneralOrder createBuyGradeOrder(UserBuyGradeForm userBuyGradeForm);

}