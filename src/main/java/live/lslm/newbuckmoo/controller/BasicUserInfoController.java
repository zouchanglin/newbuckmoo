package live.lslm.newbuckmoo.controller;

import live.lslm.newbuckmoo.catchs.VerifyKeyCatch;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.BindPhoneForm;
import live.lslm.newbuckmoo.service.SendPhoneMessageService;
import live.lslm.newbuckmoo.service.UserBasicInfoService;
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