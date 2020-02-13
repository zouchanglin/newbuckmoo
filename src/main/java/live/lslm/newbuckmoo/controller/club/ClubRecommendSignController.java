package live.lslm.newbuckmoo.controller.club;

import com.google.common.collect.Maps;
import live.lslm.newbuckmoo.dto.ClubApproveDTO;
import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.club.ClubRecommendSignForm;
import live.lslm.newbuckmoo.service.SchoolClubInfoService;
import live.lslm.newbuckmoo.service.StudentsInfoService;
import live.lslm.newbuckmoo.utils.EnumUtil;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/club/recommend")
public class ClubRecommendSignController {

    @Autowired
    private StudentsInfoService studentsInfoService;

    @Autowired
    private SchoolClubInfoService schoolClubInfoService;

    @PostMapping("sign")
    public ResultVO recommendSign(@RequestBody @Valid ClubRecommendSignForm recommendSignForm,
                                  BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【社团被推荐注册】参数不正确 {}", recommendSignForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        StudentApproveDTO studentApproveDTO = studentsInfoService.getStudentInfoByOpenId(recommendSignForm.getOpenId());
        if(studentApproveDTO == null) return ResultVOUtil.error(1, "注册为学生用户才可以注册社团");
        if(!studentApproveDTO.getAuditStatus().equals(AuditStatusEnum.AUDIT_SUCCESS.getCode())){
            return ResultVOUtil.error(2, "学生信息审核中，通过后才可以注册社团");
        }

        ClubApproveDTO clubApproveDTO = schoolClubInfoService.createStudentInfoByRecommend(recommendSignForm);
        //返回数据填充
        Map<String, Object> map = Maps.newHashMap();
        map.put("openId", clubApproveDTO.getOpenId());
        map.put("status", Objects.requireNonNull(
                EnumUtil.getByCode(clubApproveDTO.getAuditStatus(), AuditStatusEnum.class)).getMessage());
        map.put("status_code", clubApproveDTO.getAuditStatus());

        return ResultVOUtil.success(map);
    }
}