package live.lslm.newbuckmoo.controller;

import live.lslm.newbuckmoo.config.ProjectUrlConfig;
import live.lslm.newbuckmoo.constant.CookieConstant;
import live.lslm.newbuckmoo.convert.WxMpUserConvert;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.service.UserBasicInfoService;
import live.lslm.newbuckmoo.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信用户基本信息获取控制器
 */
@Slf4j
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/wechat")
public class WeChatController {
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @Autowired
    private WxMpService wxMpService;

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    /**
     * 用户信息授权
     * @param returnUrl 重定向的链接
     * @return 合并链接
     */
    @GetMapping("authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        //1、配置
        //2、调用方法
        String url = projectUrlConfig.getWechatMpAuthorize() + "/newbuckmoo/wechat/userInfo";
        String redirectUrl = null;
        try {
            redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, URLEncoder.encode(returnUrl, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "redirect:" + redirectUrl;
    }


    /**
     * 用户登录时获取用户信息
     * @param code 获取信息时的code字段
     * @param returnUrl 返回的链接
     * @param openid Cookie里面存储的openid
     * @return 重新向和并连接
     */
    @GetMapping("userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl,
                           HttpServletResponse response,
                           @CookieValue(value = "openid", required = false) String openid){
        if(openid == null){
            try {
                WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
                WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(accessToken, null);
                UserBasicInfo userBasicInfo = WxMpUserConvert.mpUserConvertToUserBasicInfo(wxMpUser);
                String savedOpenid = userBasicInfoService.updateOrCreateUserBasic(userBasicInfo);
                CookieUtil.set(response, CookieConstant.OPENID, savedOpenid, CookieConstant.EXPIRE);
            } catch (WxErrorException e) {
                e.printStackTrace();
            }
        }
        //如果openid不为空，数据库里面还没有，这种情况是不存在的
        return "redirect:" + returnUrl;
    }
}