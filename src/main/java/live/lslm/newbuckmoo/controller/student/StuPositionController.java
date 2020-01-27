package live.lslm.newbuckmoo.controller.student;

import com.google.common.collect.Lists;
import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.RequestByPageForm;
import live.lslm.newbuckmoo.form.StudentApplyPositionForm;
import live.lslm.newbuckmoo.service.PositionInfoService;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.PositionVO;
import live.lslm.newbuckmoo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/student/position")
public class StuPositionController {

    @Autowired
    private PositionInfoService positionInfoService;

    @PostMapping("list")
    public ResultVO getPositionList(@RequestBody RequestByPageForm requestByPageForm,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, {}", requestByPageForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Page<PositionInfoDTO> infoDTOPage = positionInfoService.showPositionForStudent(requestByPageForm);
        return ResultVOUtil.success(convert(infoDTOPage));
    }

    @PostMapping("apply")
    public ResultVO studentApplyPosition(@RequestBody @Valid StudentApplyPositionForm studentApplyPositionForm,
                                         BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, {}", studentApplyPositionForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        positionInfoService.applyPosition(studentApplyPositionForm);
        return ResultVOUtil.success();
    }

    /**
     * DTO->VO转换器
     * @param positionInfoDTOPage DTOPage
     * @return VO对象
     */
    private List<PositionVO> convert(Page<PositionInfoDTO> positionInfoDTOPage){
        List<PositionInfoDTO> content = positionInfoDTOPage.getContent();
        List<PositionVO> result = Lists.newArrayListWithCapacity(content.size());
        PositionVO positionVO;
        for(PositionInfoDTO positionInfoDTO: content){
            positionVO = new PositionVO();
            BeanUtils.copyProperties(positionInfoDTO, positionVO);
            positionVO.setPositionClearingWayStr(positionInfoDTO.getClearingWayEnum().getMessage());
            positionVO.setCreateTimeStr(positionInfoDTO.getCreateTime());
            positionVO.setPositionCompanyName(positionInfoDTO.getCompanyInfo().getCompanyName());
            result.add(positionVO);
        }
        return result;
    }
}