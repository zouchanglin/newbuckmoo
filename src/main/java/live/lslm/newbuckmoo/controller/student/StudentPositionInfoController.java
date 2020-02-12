package live.lslm.newbuckmoo.controller.student;

import com.google.common.collect.Lists;
import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.PositionListRequestByPageForm;
import live.lslm.newbuckmoo.form.StudentApplyPositionForm;
import live.lslm.newbuckmoo.service.PositionInfoService;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.PageResultBind;
import live.lslm.newbuckmoo.vo.PositionVO;
import live.lslm.newbuckmoo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Slf4j
@RestController
@RequestMapping("/student/position")
public class StudentPositionInfoController {

    @Autowired
    private PositionInfoService positionInfoService;

    @PostMapping("add-browse")
    public ResultVO addPositionBrowse(@RequestBody Map<String, Object> map){
        String positionId = (String)map.get("positionId");
        positionInfoService.addPositionBrowse(positionId);
        return ResultVOUtil.success();
    }

    @PostMapping("list")
    //@Cacheable(cacheNames = "positionDTOPage", key = "001")
    public ResultVO getPositionList(@RequestBody @Valid PositionListRequestByPageForm requestByPageForm,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, {}", requestByPageForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        //Page<PositionInfoDTO> infoDTOPage = positionInfoService.showPositionForStudent(requestByPageForm);
        Page<PositionInfoDTO> infoDTOPage = positionInfoService.showPositionForStudentByTag(requestByPageForm);

        List<PositionVO> convert = convert(infoDTOPage);

        PageResultBind<List<PositionVO>> pageResultBind = new PageResultBind<>();
        pageResultBind.setSize(requestByPageForm.getSize());
        pageResultBind.setCurrentPage(requestByPageForm.getPage());
        pageResultBind.setTotalPage(infoDTOPage.getTotalPages());
        pageResultBind.setData(convert);

        return ResultVOUtil.success(pageResultBind);
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
            positionVO.setCreateTime(positionInfoDTO.getCreateTimeLong());
            positionVO.setPositionCompanyName(positionInfoDTO.getCompanyInfo().getCompanyName());
            result.add(positionVO);
        }
        return result;
    }
}