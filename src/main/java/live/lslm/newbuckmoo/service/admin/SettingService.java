package live.lslm.newbuckmoo.service.admin;

import com.google.gson.internal.$Gson$Types;
import live.lslm.newbuckmoo.entity.SystemSettings;
import live.lslm.newbuckmoo.form.admin.GradeSettingForm;
import lombok.Data;

import java.util.List;

public interface SettingService {


    /* 获取所有积分相关配置 */
    List<SystemSettings> getAllGradeSetting();

    /* 保存积分相关配置 */
    List<SystemSettings> saveAllGradeSetting(GradeSettingForm gradeSettingForm);

    /* 获取某一项设置 */
    SystemSettings getOneSetting(SettingEnum settingEnum);
}