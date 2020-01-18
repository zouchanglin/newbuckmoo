package live.lslm.newbuckmoo.controller.admin;

import live.lslm.newbuckmoo.constant.CookieConstant;
import live.lslm.newbuckmoo.constant.RedisConstant;
import live.lslm.newbuckmoo.entity.SystemSettings;
import live.lslm.newbuckmoo.repository.SystemSettingsRepository;
import live.lslm.newbuckmoo.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/admin/login")
public class AdminLoginController {
    @Autowired
    private SystemSettingsRepository settingsRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("page")
    public ModelAndView getLoginPage(){
        return new ModelAndView("login");
    }

    @GetMapping("logout")
    public ModelAndView adminLogout(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Map<String, Object> map){
        //1、查询Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null){
            //2、清除Redis
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));

            //3、清除Cookie
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        map.put("msg", "登出成功");
        map.put("url", "admin/login/page");
        return new ModelAndView("common/success");
    }

    @PostMapping("verify")
    public ModelAndView adminLogin(@RequestParam String adminId,
                                   @RequestParam String adminPass,
                                   HttpServletResponse servletResponse,
                                   Map<String, Object> map){
        if(StringUtils.isEmpty(adminId) | StringUtils.isEmpty(adminPass)){
            map.put("msg", "用户名和密码缺失");
            map.put("url", "/admin/login/page");
            return new ModelAndView("common/error");
        }

        //查看数据库Admin账户配置
        Optional<SystemSettings> idOpt = settingsRepository.findById("root_id");
        if(!idOpt.isPresent()) throw new RuntimeException("[数据库设置 root_id丢失]");

        Optional<SystemSettings> pwdOpt = settingsRepository.findById("root_pwd");
        if(!pwdOpt.isPresent()) throw new RuntimeException("[数据库设置 root_pwd丢失]");

        //去数据库匹配
        if(!idOpt.get().getSystemValue().equals(adminId) || !pwdOpt.get().getSystemValue().equals(adminPass)){
            //登录失败
            map.put("msg", "用户名或密码错误");
            map.put("url", "admin/login/page");
            return new ModelAndView("common/error", map);
        }

        //登录成功
        String token = UUID.randomUUID().toString();
        int expire = RedisConstant.EXPIRE;

        //设置token至Redis
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), adminId, expire, TimeUnit.SECONDS);

        //设置Cookie
        CookieUtil.set(servletResponse, CookieConstant.TOKEN, token, expire);
        map.put("msg", "登陆成功");
        map.put("url", "admin/center");
        return new ModelAndView("common/success", map);
    }
}