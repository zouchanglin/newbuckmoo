package live.lslm.newbuckmoo.form.club;

import live.lslm.newbuckmoo.form.SchoolClubAttestationForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
public class ClubRecommendSignForm extends SchoolClubAttestationForm {

    @NotEmpty(message = "邀请码必填")
    private String recommendCode;
}