package live.lslm.newbuckmoo.form;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class PositionListRequestByPageForm extends RequestByPageForm implements Serializable {
    private static final long serialVersionUID = 2207828125255413902L;

    @NotNull(message = "缺少参数Tag")
    @Min(message = "类型ID必须>0", value = -1)
    private Integer tag;
}
