package live.lslm.newbuckmoo.controller.admin.setting;

import live.lslm.newbuckmoo.entity.GradeCombo;
import live.lslm.newbuckmoo.service.grade.GradeComboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin/setting/grade-combo")
public class GradeComboSettingController {
    @Autowired
    private GradeComboService gradeComboService;

    @GetMapping("editor")
    public ModelAndView getEditor(Map<String, Object> map){
        map.put("gradeComboList", gradeComboService.getAllGradeCombo());
        return new ModelAndView("setting/grade-combo-setting", map);
    }

    @GetMapping("save")
    public ModelAndView save(GradeCombo gradeCombo){
        gradeComboService.updateOneCombo(gradeCombo);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/setting/grade-combo/editor");
        return modelAndView;
    }

    @GetMapping("add")
    public ModelAndView add(GradeCombo gradeCombo){
        gradeComboService.addOneCombo(gradeCombo);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/setting/grade-combo/editor");
        return modelAndView;
    }

    @GetMapping("delete")
    public ModelAndView deleteOne(Integer gradeComboId){
        gradeComboService.deleteOneCombo(gradeComboId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/setting/grade-combo/editor");
        return modelAndView;
    }
}