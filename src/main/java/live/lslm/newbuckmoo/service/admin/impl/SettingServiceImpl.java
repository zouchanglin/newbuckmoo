package live.lslm.newbuckmoo.service.admin.impl;

import com.google.common.collect.Lists;
import live.lslm.newbuckmoo.entity.SystemSettings;
import live.lslm.newbuckmoo.form.admin.GradeSettingForm;
import live.lslm.newbuckmoo.repository.SystemSettingsRepository;
import live.lslm.newbuckmoo.service.admin.SettingEnum;
import live.lslm.newbuckmoo.service.admin.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SystemSettingsRepository settingsRepository;

    @Override
    public SystemSettings getOneSetting(SettingEnum settingEnum) {
        return settingsRepository.getOne(settingEnum.getKey());
    }

    @Override
    public List<SystemSettings> saveAllGradeSetting(GradeSettingForm gradeSettingForm) {

        List<SystemSettings> settingList = settingsRepository.findAll();
        for(SystemSettings setting: settingList){
            String systemKey = setting.getSystemKey();
            switch (systemKey){
                case "new_club":
                    setting.setSystemValue(gradeSettingForm.getNew_club());
                    settingsRepository.save(setting);
                    break;
                case "new_student":
                    setting.setSystemValue(gradeSettingForm.getNew_student());
                    settingsRepository.save(setting);
                    break;
                case "new_company":
                    setting.setSystemValue(gradeSettingForm.getNew_company());
                    settingsRepository.save(setting);
                    break;
                case "recommend_club":
                    setting.setSystemValue(gradeSettingForm.getRecommend_club());
                    settingsRepository.save(setting);
                    break;
                case "recommend_student":
                    setting.setSystemValue(gradeSettingForm.getRecommend_student());
                    settingsRepository.save(setting);
                    break;
                case "recommend_company":
                    setting.setSystemValue(gradeSettingForm.getRecommend_company());
                    settingsRepository.save(setting);
                    break;
                default:
                    log.error("【更新配置】更新配置时遇到不合法配置");
            }
        }
        return getAllGradeSetting();
    }

    @Override
    public List<SystemSettings> getAllGradeSetting() {
        List<SystemSettings> retList = Lists.newArrayList();

        retList.add(settingsRepository.getOne("new_club"));
        retList.add(settingsRepository.getOne("new_student"));
        retList.add(settingsRepository.getOne("new_company"));

        retList.add(settingsRepository.getOne("recommend_club"));
        retList.add(settingsRepository.getOne("recommend_student"));
        retList.add(settingsRepository.getOne("recommend_company"));

        return retList;
    }
}
