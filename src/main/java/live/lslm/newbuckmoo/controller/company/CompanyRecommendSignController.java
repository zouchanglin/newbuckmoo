package live.lslm.newbuckmoo.controller.company;

import com.google.common.collect.Maps;
import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.company.CompanyRecommendSignForm;
import live.lslm.newbuckmoo.service.CompanyInfoService;
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
@RequestMapping("/company/recommend")
public class CompanyRecommendSignController {
    @Autowired
    private CompanyInfoService companyInfoService;

    @PostMapping("sign")
    public ResultVO recommendSign(@RequestBody @Valid CompanyRecommendSignForm recommendSignForm,
                                  BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("【企业被推荐注册】参数不合法 {}", recommendSignForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        CompanyApproveDTO approveDTO = companyInfoService.createCompanyInfoByRecommend(recommendSignForm);

        //返回数据填充
        Map<String, Object> map = Maps.newHashMap();
        map.put("openId", approveDTO.getOpenId());
        map.put("status", Objects.requireNonNull(EnumUtil.getByCode(approveDTO.getAuditStatus(), AuditStatusEnum.class)).getMessage());
        map.put("status_code", approveDTO.getAuditStatus());

        return ResultVOUtil.success(map);
    }
}