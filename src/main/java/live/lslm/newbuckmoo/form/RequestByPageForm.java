package live.lslm.newbuckmoo.form;

import lombok.Data;

@Data
public class RequestByPageForm implements BasicForm {
    private Integer page;
    private Integer size;
    private String openId;
}
