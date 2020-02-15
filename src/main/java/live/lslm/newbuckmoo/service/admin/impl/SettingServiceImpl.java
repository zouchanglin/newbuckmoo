package live.lslm.newbuckmoo.service.admin.impl;

import com.google.common.collect.Lists;
import live.lslm.newbuckmoo.entity.SystemSettings;
import live.lslm.newbuckmoo.form.admin.GradeSettingForm;
import live.lslm.newbuckmoo.repository.SystemSettingsRepository;
import live.lslm.newbuckmoo.service.admin.SettingEnum;
import live.lslm.newbuckmoo.service.admin.SettingService;
import live.lslm.newbuckmoo.utils.EnumUtil;
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
    public void saveAllGradeSetting(GradeSettingForm gradeSettingForm) {
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
                case "root_id":
                    break;
                case "root_pwd":
                    break;
                case "admin_open_id":
                    break;
                default:
                    log.error("【更新配置】更新配置时遇到不合法配置");
            }
        }
    }

    @Override
    public List<SystemSettings> getAllGradeSetting() {
        List<SystemSettings> retList = Lists.newArrayList();
        SettingEnum[] allEnums = EnumUtil.getAllEnums(SettingEnum.class);
        for(SettingEnum settingEnum: allEnums){
            retList.add(settingsRepository.getOne(settingEnum.getKey()));
        }
//        retList.add(settingsRepository.getOne(SettingEnum.NEW_CLUB.getKey()));
//        retList.add(settingsRepository.getOne(SettingEnum.NEW_STUDENT.getKey()));
//        retList.add(settingsRepository.getOne(SettingEnum.NEW_COMPANY.getKey()));
//
//        retList.add(settingsRepository.getOne(SettingEnum.RECOMMEND_CLUB.getKey()));
//        retList.add(settingsRepository.getOne(SettingEnum.RECOMMEND_STUDENT.getKey()));
//        retList.add(settingsRepository.getOne(SettingEnum.RECOMMEND_COMPANY.getKey()));

        return retList;
    }
}
