package live.lslm.newbuckmoo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ShowPositionApplyFrom {
    /**
     * openId
     */
    @NotEmpty(message = "openId丢失")
    private String openId;

    /**
     * 兼职Id
     */
    @NotEmpty(message = "positionId(兼职信息ID)必填")
    private String positionId;

    /**
     * 页
     */
    @NotNull(message = "缺少分页参数")
    private Integer page;

    /**
     * 大小
     */
    @NotNull(message = "缺少分页参数")
    private Integer size;
}
