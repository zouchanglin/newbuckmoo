package live.lslm.newbuckmoo.exception;

import live.lslm.newbuckmoo.enums.ResultEnum;
import lombok.Getter;

@Getter
public class BuckmooException extends RuntimeException{
    private Integer code;

    public BuckmooException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public BuckmooException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}