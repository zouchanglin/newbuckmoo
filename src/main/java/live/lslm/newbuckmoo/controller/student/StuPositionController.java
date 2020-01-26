package live.lslm.newbuckmoo.controller.student;

import com.google.common.collect.Lists;
import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.form.RequestByPageForm;
import live.lslm.newbuckmoo.service.PositionInfoService;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.PositionVO;
import live.lslm.newbuckmoo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/student/position")
public class StuPositionController {

    @Autowired
    private PositionInfoService positionInfoService;

    @PostMapping("list")
    public ResultVO getPositionList(@RequestBody RequestByPageForm requestByPageForm){
        Page<PositionInfoDTO> infoDTOPage = positionInfoService.showPositionForStudent(requestByPageForm);
        return ResultVOUtil.success(convert(infoDTOPage));
    }


    private List<PositionVO> convert(Page<PositionInfoDTO> positionInfoDTOPage){
        List<PositionInfoDTO> content = positionInfoDTOPage.getContent();
        List<PositionVO> result = Lists.newArrayListWithCapacity(content.size());
        PositionVO positionVO;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
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