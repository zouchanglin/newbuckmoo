package live.lslm.newbuckmoo.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RequestByPageForm implements BasicForm {

    @NotNull(message = "缺少分页参数Page")
    @Min(message = "分页index至少为0", value = 0)
    private Integer page;

    @NotNull(message = "缺少分页参数Page")
    @Min(message = "分页大小至少为1", value = 1)
    private Integer size;

    @NotEmpty(message = "openID丢失")
    private String openId;
}
