package live.lslm.newbuckmoo.controller;

import com.google.common.collect.Maps;
import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.entity.SchoolClubInfo;
import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.CompanyAttestationForm;
import live.lslm.newbuckmoo.form.SchoolClubForm;
import live.lslm.newbuckmoo.form.StudentAttestationForm;
import live.lslm.newbuckmoo.service.CompanyInfoService;
import live.lslm.newbuckmoo.service.SchoolClubInfoService;
import live.lslm.newbuckmoo.service.StudentsInfoService;
import live.lslm.newbuckmoo.service.WebSocket;
import live.lslm.newbuckmoo.utils.EnumUtil;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

/**
 * 学生、企业、社团信息认证控制器
 */
@Slf4j
@RestController
@RequestMapping("/approve")
public class AttestationController {
    @Autowired
    private StudentsInfoService studentsInfoService;

    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private SchoolClubInfoService schoolClubInfoService;

    @Autowired
    private WebSocket webSocket;

    /**
     * 社团信息注册
     * @param schoolClubForm 社团信息注册
     * @param bindingResult 出错绑定
     * @return openId和审核状态
     */
    @PostMapping(value = "club", produces = "application/json")
    public ResultVO attestationForClub(@RequestBody @Valid SchoolClubForm schoolClubForm,
                                          BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, schoolClubForm={}", schoolClubForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info("[AttestationController] schoolClubForm={}", schoolClubForm);
        SchoolClubInfo schoolClubInfo = schoolClubInfoService.createOrUpdateInfo(schoolClubForm);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("openId", schoolClubInfo.getOpenId());
        Integer auditStatus = schoolClubInfo.getAuditStatus();
        resultMap.put("status_code", auditStatus);
        resultMap.put("status", Objects.requireNonNull(EnumUtil.getByCode(auditStatus, AuditStatusEnum.class)).getMessage());

        //通知后台管理
        webSocket.sendMessage("新的社团注册信息有待审核哟 &/admin/approve/club-list");
        return ResultVOUtil.success(resultMap);
    }

    /**
     * 企业信息注册
     * @param companyAttestationForm 企业信息表单
     * @param bindingResult 出错处理绑定
     * @return openId和审核状态
     */
    @PostMapping(value = "company", produces = "application/json")
    public ResultVO attestationForCompany(@RequestBody @Valid CompanyAttestationForm companyAttestationForm,
                                          BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, companyAttestationForm={}", companyAttestationForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info("[AttestationController] companyAttestationForm={}", companyAttestationForm);
        CompanyInfo savedCompanyInfo = companyInfoService.createOrUpdateInfo(companyAttestationForm);
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("openID", savedCompanyInfo.getOpenId());
        Integer auditStatus = savedCompanyInfo.getAuditStatus();
        resultMap.put("status_code", auditStatus);
        resultMap.put("status", Objects.requireNonNull(EnumUtil.getByCode(auditStatus, AuditStatusEnum.class)).getMessage());
        //通知后台管理
        webSocket.sendMessage("新的社团注册信息有待审核哟 &/admin/approve/company-list");
        return ResultVOUtil.success(resultMap);
    }

    /**
     * 学生信息注册JSON请求格式
     * @param studentAttestationForm 请求表单对象(openid来自cookie)
     * @param bindingResult 出错处理绑定
     * @return openId和审核状态
     */
    @PostMapping(value = "student", produces = "application/json")
    public ResultVO attestationForStudent(@RequestBody @Valid StudentAttestationForm studentAttestationForm,
                        BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("参数不正确, studentAttestationForm={}", studentAttestationForm);
            throw new BuckmooException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        log.info("[StudentAttestationController] studentAttestationForm={}", studentAttestationForm);
        StudentInfo studentInfo = studentsInfoService.createOrUpdateInfo(studentAttestationForm);

        Map<String, Object> map = Maps.newHashMap();
        map.put("openId", studentInfo.getOpenId());
        map.put("status", Objects.requireNonNull(EnumUtil.getByCode(studentInfo.getAuditStatus(), AuditStatusEnum.class)).getMessage());
        map.put("status_code", studentInfo.getAuditStatus());

        //通知后台管理
        webSocket.sendMessage("新的社团注册信息有待审核哟 &/admin/approve/student-list");
        return ResultVOUtil.success(map);
    }
}