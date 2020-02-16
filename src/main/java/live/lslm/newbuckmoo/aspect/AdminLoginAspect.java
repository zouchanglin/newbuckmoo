package live.lslm.newbuckmoo.aspect;

import live.lslm.newbuckmoo.constant.CookieConstant;
import live.lslm.newbuckmoo.constant.RedisConstant;
import live.lslm.newbuckmoo.exception.BuckmooAuthorizeException;
import live.lslm.newbuckmoo.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 管理员操作拦截器
 */
@Slf4j
@Aspect
@Component
public class AdminLoginAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //本来返回值是org.springframework.web.servlet.ModelAndView
    @Before("execution(public * live.lslm.newbuckmoo.controller.admin.*.*(..))" +
    "&& !execution(public * live.lslm.newbuckmoo.controller.admin.AdminLoginController.*(..))")
    public void isAdmin() {
        log.info("【管理员操作验证】");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        //查询Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie == null){
            log.warn("【管理员登录校验】 Cookie中查不到token");
            throw new BuckmooAuthorizeException();
        }

        //去Redis里面查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("【管理员登录校验】 Redis中查不到token");
            throw new BuckmooAuthorizeException();
        }
    }

    @Before("execution(public * live.lslm.newbuckmoo.controller.admin.setting.*.*(..))")
    public void isAdminSetting(){
        isAdmin();
    }
}