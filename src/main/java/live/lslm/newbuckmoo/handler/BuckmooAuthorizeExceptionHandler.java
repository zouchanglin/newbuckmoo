package live.lslm.newbuckmoo.handler;

import com.google.common.collect.Maps;
import live.lslm.newbuckmoo.exception.BuckmooAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Slf4j
@ControllerAdvice
public class BuckmooAuthorizeExceptionHandler {
    /**
     * 管理员登录的异常拦截
     * @param e 捕获的异常
     * @return 处理结果
     */
    @ExceptionHandler(value = BuckmooAuthorizeException.class)
    public ModelAndView handlerBuckmooException(BuckmooAuthorizeException e){
        log.error("【管理员登录的异常拦截】{}", e);
        Map<String, Object> map = Maps.newHashMap();
        map.put("msg", "请先登录后台系统");
        map.put("url", "admin/login/page");
        return new ModelAndView("/common/error", map);
    }
}