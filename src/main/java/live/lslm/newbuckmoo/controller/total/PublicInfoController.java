package live.lslm.newbuckmoo.controller.total;

import live.lslm.newbuckmoo.entity.LongTextStorage;
import live.lslm.newbuckmoo.repository.LongTextStorageRepository;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/total/show")
public class PublicInfoController {
    @Autowired
    private LongTextStorageRepository textStorageRepository;

    @GetMapping("service-agree")
    public ResultVO getServiceAgree() {
        Optional<LongTextStorage> serviceAgree = textStorageRepository.findById("service_agree");
        return serviceAgree.map(longTextStorage -> ResultVOUtil.success(longTextStorage.getArgsText())).orElseGet(() -> ResultVOUtil.success(""));
    }
}