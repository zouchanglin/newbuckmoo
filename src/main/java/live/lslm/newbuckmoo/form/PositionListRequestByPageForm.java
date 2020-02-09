package live.lslm.newbuckmoo.form;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class PositionListRequestByPageForm extends RequestByPageForm {

    @NotNull(message = "缺少参数Tag")
    @Min(message = "类型ID必须>0", value = -1)
    private Integer tag;
}
