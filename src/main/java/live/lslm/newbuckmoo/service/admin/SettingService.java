package live.lslm.newbuckmoo.service.admin;

import live.lslm.newbuckmoo.entity.SystemSettings;
import live.lslm.newbuckmoo.form.admin.GradeSettingForm;

import java.util.List;

public interface SettingService {


    /* 获取所有积分相关配置 */
    List<SystemSettings> getAllGradeSetting();

    /* 保存积分相关配置 */
    void saveAllGradeSetting(GradeSettingForm gradeSettingForm);

    /* 获取某一项设置 */
    SystemSettings getOneSetting(SettingEnum settingEnum);
}