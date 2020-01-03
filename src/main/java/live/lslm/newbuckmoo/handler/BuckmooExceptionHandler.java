package live.lslm.newbuckmoo.handler;

import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.utils.ResultVOUtil;
import live.lslm.newbuckmoo.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class BuckmooExceptionHandler {

    /**
     * 处理控制层 or 服务层异常
     * @param e 捕获的异常
     * @return 处理结果
     */
    @ExceptionHandler(value = BuckmooException.class)
    @ResponseBody
    public ResultVO handlerBuckmooException(BuckmooException e){
        return ResultVOUtil.error(e.getCode(), e.getMessage());
    }
}
