package live.lslm.newbuckmoo.controller;

import com.google.common.collect.Maps;
import live.lslm.newbuckmoo.catchs.VerifyKeyCatch;
import live.lslm.newbuckmoo.dto.ClubApproveDTO;
import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.BindPhoneForm;
import live.lslm.newbuckmoo.service.*;
import live.lslm.newbuckmoo.utils.KeyUtil;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户基本信息控制器
 */
@Slf4j
@RestController
@RequestMapping("/basic-info")
public class BasicUserInfoController {
    @Autowired
    private UserBasicInfoService userBasicInfoService;

    @Autowired
    private SendPhoneMessageService phoneMessageService;

    @Autowired
    private CompanyInfoService companyInfoService;

    @Autowired
    private SchoolClubInfoService clubInfoService;

    @Autowired
    private StudentsInfoService studentInfoService;

    /**
     * TODO是学生、企业、社团都要展示出来
     */
    //TODO 一个一个展示还是直接展示
    @PostMapping("/getUserInfo")
    public ResultVO getUserInfo(@RequestBody Map<String, Object> map){
        String openId = (String) map.get("openId");
        if(StringUtils.isEmpty(openId)) throw new BuckmooException(ResultEnum.PARAM_ERROR);
        CompanyApproveDTO companyApproveDTO = companyInfoService.getCompanyByOpenId(openId);
        ClubApproveDTO clubApproveDTO = clubInfoService.getClubInfoByOpenId(openId);
        StudentApproveDTO studentApproveDTO = studentInfoService.getStudentInfoByOpenId(openId);

        Map<String, Object> resultMap = Maps.newHashMap();
        if(companyApproveDTO != null) resultMap.put("company", companyApproveDTO);
        if(clubApproveDTO != null) resultMap.put("club", clubApproveDTO);
        if(studentApproveDTO != null) resultMap.put("student", studentApproveDTO);
        return ResultVOUtil.success(resultMap);
    }


    /**
     * 获取验证码
     * @param map 其中的唯一字段：手机号
     * @return 发送结果
     */
    @PostMapping(value = "verify-key")
    public ResultVO verifyKey(@RequestBody Map<String, Object> map){
        String phone = (String)map.get("phone");
        if(StringUtils.isEmpty(phone)){
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
        try{
            String verifyKey = KeyUtil.genVerifyKey();
            phoneMessageService.sendMsg(phone, verifyKey);
            VerifyKeyCatch.putVerifyKey(phone, verifyKey);
            return ResultVOUtil.success();
        }catch (Exception e){
            log.error("[发送短信失败] e={}", e);
            ResultEnum messageError = ResultEnum.SEND_MESSAGE_ERROR;
            return ResultVOUtil.error(messageError.getCode(), messageError.getMessage());
        }
    }

    /**
     * @param bindPhoneForm 绑定手机号的表单
     * @return 绑定结果
     */
    @PostMapping(value = "bind-phone")
    public ResultVO bindPhone(@RequestBody BindPhoneForm bindPhoneForm){
        System.out.println(bindPhoneForm);
        try{
            userBasicInfoService.bindPhoneForUser(bindPhoneForm);
            return ResultVOUtil.success();
        }catch (BuckmooException e){
            return ResultVOUtil.error(1, "验证码错误");
        }
    }
}