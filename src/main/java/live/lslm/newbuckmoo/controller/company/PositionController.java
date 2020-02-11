package live.lslm.newbuckmoo.controller.company;

import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.entity.CategoryInfo;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.PositionInfoForm;
import live.lslm.newbuckmoo.form.RequestByPageForm;
import live.lslm.newbuckmoo.form.ShowPositionApplyFrom;
import live.lslm.newbuckmoo.service.PositionInfoService;
import live.lslm.newbuckmoo.service.StudentResumeService;
import live.lslm.newbuckmoo.service.WechatPushMessageService;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.PageResultBind;
import live.lslm.newbuckmoo.vo.ResultVO;
import live.lslm.newbuckmoo.vo.StudentResumeVO;
import live.lslm.newbuckmoo.vo.StudentVO;
import live.lslm.newbuckmoo.vo.company.PositionForCompanyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/company/position")
public class PositionController {
    @Autowired
    private PositionInfoService positionInfoService;

    @Autowired
    private WechatPushMessageService wechatPushMessageService;

    @Autowired
    private StudentResumeService studentResumeService;


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

    /**
     * 企业用户查看自己发布的兼职
     * @param requestByPageForm 分页请求表单
     * @param bindingResult 分页查询结果
     */
    @PostMapping("my-list")
    public ResultVO getPositionList(@RequestBody RequestByPageForm requestByPageForm,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, {}", requestByPageForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Page<PositionForCompanyVO> infoDTOPage = positionInfoService.showMySelfCratedPosition(requestByPageForm);
        List<PositionForCompanyVO> convert = infoDTOPage.getContent();

        PageResultBind<List<PositionForCompanyVO>> pageResultBind = new PageResultBind<>();
        pageResultBind.setSize(requestByPageForm.getSize());
        pageResultBind.setCurrentPage(requestByPageForm.getPage());
        pageResultBind.setTotalPage(infoDTOPage.getTotalPages());
        pageResultBind.setData(convert);

        return ResultVOUtil.success(pageResultBind);
    }

    /**
     * 企业查看某个兼职的申请列表
     */
    @PostMapping("apply-list")
    public ResultVO getApplyListForPosition(@RequestBody ShowPositionApplyFrom showPositionApplyFrom,
                                            BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, {}", showPositionApplyFrom);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        Page<StudentVO> studentVOS = positionInfoService.showMyPositionApply(showPositionApplyFrom);

        PageResultBind<List<StudentVO>> pageResultBind = new PageResultBind<>();
        pageResultBind.setCurrentPage(showPositionApplyFrom.getPage());
        pageResultBind.setSize(showPositionApplyFrom.getSize());
        pageResultBind.setData(studentVOS.getContent());
        pageResultBind.setTotalPage(studentVOS.getTotalPages());

        return ResultVOUtil.success(pageResultBind);
    }

    /**
     * 企业查看申请人简历
     * @return 简历
     */
    @PostMapping("applicant-resume")
    public ResultVO getApplicantResume(@RequestBody Map<String, Object> map){
        String studentId = (String) map.get("studentId");
        StudentResumeVO studentResumeVO = studentResumeService.getOneResumeByStudentId(studentId);
        return ResultVOUtil.success(studentResumeVO);
    }
}