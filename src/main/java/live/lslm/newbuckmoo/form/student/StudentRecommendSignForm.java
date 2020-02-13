package live.lslm.newbuckmoo.form.student;

import live.lslm.newbuckmoo.form.StudentAttestationForm;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentRecommendSignForm extends StudentAttestationForm {
    @NotEmpty(message = "推荐码必填")
    private String recommendCode;
}