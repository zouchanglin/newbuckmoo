package live.lslm.newbuckmoo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/center")
public class IndexController {
    @GetMapping
    public ModelAndView index(){
        return new ModelAndView("center");
    }
}