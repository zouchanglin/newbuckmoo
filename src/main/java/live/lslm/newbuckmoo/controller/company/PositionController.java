package live.lslm.newbuckmoo.controller.company;

import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.entity.CategoryInfo;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.PositionInfoForm;
import live.lslm.newbuckmoo.service.PositionInfoService;
import live.lslm.newbuckmoo.service.WechatPushMessageService;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/company/position")
public class PositionController {
    @Autowired
    private PositionInfoService positionInfoService;

    @Autowired
    private WechatPushMessageService wechatPushMessageService;

    /**
     * 发布兼职
     */
    @PostMapping("create")
    public ResultVO createOrUpdatePosition(@RequestBody @Valid PositionInfoForm positionInfoForm,
                               BindingResult bindingResult){
        log.info("[新增兼职信息] positionInfoForm={}", positionInfoForm);
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, schoolClubForm={}", positionInfoForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        PositionInfoDTO exeResult = positionInfoService.createOrUpdatePosition(positionInfoForm);
        wechatPushMessageService.positionApproveResultStatus(exeResult);

        log.info("[新增兼职信息] exeResult={}", exeResult);
        return exeResult != null ? ResultVOUtil.success(): ResultVOUtil.error(1, "保存失败");
    }


    /**
     * 获取兼职分类信息
     * @return 兼职分类信息
     */
    @GetMapping("categories")
    public ResultVO getCategoryList(){
        List<CategoryInfo> ret = positionInfoService.getAllCategoryInfo();
        return ResultVOUtil.success(ret);
    }
}