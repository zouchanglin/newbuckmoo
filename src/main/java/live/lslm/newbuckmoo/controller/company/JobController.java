package live.lslm.newbuckmoo.controller.company;

import live.lslm.newbuckmoo.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/company/job")
public class JobController {

    //TODO 发布兼职
    @PostMapping("create")
    public ResultVO getJobList(@RequestBody Map<String, Object> map){

        return null;
    }
}
