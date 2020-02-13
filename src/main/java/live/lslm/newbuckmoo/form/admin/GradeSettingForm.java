package live.lslm.newbuckmoo.form.admin;

import lombok.Data;

/**
 * 配置项表单
 */
@Data
public class GradeSettingForm {
    private String new_club;
    private String new_student;
    private String new_company;
    private String recommend_company;
    private String recommend_student;
    private String recommend_club;
}
