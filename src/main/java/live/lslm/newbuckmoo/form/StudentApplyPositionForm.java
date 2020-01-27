package live.lslm.newbuckmoo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * student apply form
 */
@Data
public class StudentApplyPositionForm {

    @NotEmpty(message = "openId丢失")
    private String openId;

    @NotEmpty(message = "positionId必填")
    private String positionId;
}