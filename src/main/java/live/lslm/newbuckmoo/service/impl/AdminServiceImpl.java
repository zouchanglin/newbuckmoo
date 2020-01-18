package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.constant.CookieConstant;
import live.lslm.newbuckmoo.constant.RedisConstant;
import live.lslm.newbuckmoo.entity.SystemSettings;
import live.lslm.newbuckmoo.repository.SystemSettingsRepository;
import live.lslm.newbuckmoo.service.AdminService;
import live.lslm.newbuckmoo.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private SystemSettingsRepository settingsRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Boolean adminLogin(String adminId, String adminPass, HttpServletResponse httpResponse) {
        Optional<SystemSettings> idOpt = settingsRepository.findById("root_id");
        if(!idOpt.isPresent()) throw new RuntimeException("[数据库设置 root_id丢失]");

        Optional<SystemSettings> pwdOpt = settingsRepository.findById("root_pwd");
        if(!pwdOpt.isPresent()) throw new RuntimeException("[数据库设置 root_pwd丢失]");

        if(!idOpt.get().getSystemValue().equals(adminId) || !pwdOpt.get().getSystemValue().equals(adminPass)){
            //登录失败
            return false;
        }else {
            //登录成功
            String token = UUID.randomUUID().toString();
            int expire = RedisConstant.EXPIRE;

            stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), adminId, expire, TimeUnit.SECONDS);
            CookieUtil.set(httpResponse, CookieConstant.TOKEN, token, expire);
        }
        return true;
    }
}