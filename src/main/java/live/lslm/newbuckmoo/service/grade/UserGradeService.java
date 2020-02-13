package live.lslm.newbuckmoo.service.grade;

public interface UserGradeService {
    /**
     * 注册新用户：初始化积分
     * @param openId openId
     */
    void registerNewUserInitGrade(String openId);

    void registerNewUserRewardGrade(String openId);
}