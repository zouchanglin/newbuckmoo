package live.lslm.newbuckmoo.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class RequestByPageForm implements BasicForm, Serializable {
    private static final long serialVersionUID = -4936787790796820212L;
    @NotNull(message = "缺少分页参数Page")
    @Min(message = "分页index至少为0", value = 0)
    private Integer page;

    @NotNull(message = "缺少分页参数Page")
    @Min(message = "分页大小至少为1", value = 1)
    private Integer size;

    @NotEmpty(message = "openID丢失")
    private String openId;
}
