package live.lslm.newbuckmoo.controller.develop;

import live.lslm.newbuckmoo.config.ProjectUrlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/develop")
public class SocketController {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;
    @GetMapping("websocket")
    public ModelAndView getIndex(Map<String, Object> map){
        //map.put("projectUrl", "localhost:8080");
        map.put("projectUrl", projectUrlConfig.getWebsocket());
        return new ModelAndView("develop/websocket", map);
    }
}
