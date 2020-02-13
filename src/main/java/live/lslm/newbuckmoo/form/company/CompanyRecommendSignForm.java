package live.lslm.newbuckmoo.form.company;

import live.lslm.newbuckmoo.form.CompanyAttestationForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompanyRecommendSignForm extends CompanyAttestationForm {
    @NotEmpty(message = "推荐码必填")
    private String recommendCode;
}
