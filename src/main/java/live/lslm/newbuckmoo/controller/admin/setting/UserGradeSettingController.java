package live.lslm.newbuckmoo.controller.admin.setting;

import live.lslm.newbuckmoo.entity.SystemSettings;
import live.lslm.newbuckmoo.form.admin.GradeSettingForm;
import live.lslm.newbuckmoo.service.admin.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/setting/grade")
public class UserGradeSettingController {

    @Autowired
    private SettingService settingService;

    @GetMapping("editor")
    public ModelAndView getEditPage(Map<String, Object> map){
        List<SystemSettings> settingsList = settingService.getAllGradeSetting();
        map.put("settingList", settingsList);
        return new ModelAndView("/setting/user-setting", map);
    }

    @GetMapping("save")
    public ModelAndView saveSettings(GradeSettingForm gradeSettingForm,
                                     Map<String, Object> map){
        System.out.println(gradeSettingForm);
        List<SystemSettings> settingsList = settingService.saveAllGradeSetting(gradeSettingForm);
        map.put("settingList", settingsList);
        return new ModelAndView("/setting/user-setting", map);
    }
}