package live.lslm.newbuckmoo.controller.admin;

import live.lslm.newbuckmoo.entity.LongTextStorage;
import live.lslm.newbuckmoo.repository.LongTextStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Optional;

/**
 * 编写服务协议的地方
 */
@Controller
@RequestMapping("/admin/clause")
public class ServiceAgreeController {
    @Autowired
    private LongTextStorageRepository repository;

    @GetMapping("edit")
    public ModelAndView getEditPage(Map<String, Object> map){
        Optional<LongTextStorage> textStorage = repository.findById("service_agree");
        if(textStorage.isPresent()){
            String argsText = textStorage.get().getArgsText();
            map.put("serviceAgree", StringUtils.isEmpty(argsText) ? "" : argsText);
        }else{
            map.put("serviceAgree", "请编写平台服务协议");
        }
        return new ModelAndView("/other/agreement", map);
    }

    @PostMapping("save")
    public ModelAndView savePage(String text, Map<String, Object> map){
        LongTextStorage serviceAgree = new LongTextStorage();
        serviceAgree.setArgsName("service_agree");
        serviceAgree.setArgsText(StringUtils.isEmpty(text) ? "" : text);
        repository.save(serviceAgree);

        map.put("msg", "保存成功");
        map.put("url", "admin/center");
        return new ModelAndView("common/success");
    }
}
