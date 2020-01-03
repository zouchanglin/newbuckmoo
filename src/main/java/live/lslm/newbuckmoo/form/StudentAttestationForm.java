package live.lslm.newbuckmoo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 学生信息认证表单
 */
@Data
public class StudentAttestationForm {
    /**
     * 学生学号
     */
    @NotEmpty(message = "学号必填")
    private String studentId;

    /**
     * 学生姓名
     */
    @NotEmpty(message = "姓名必填")
    private String studentName;

    /**
     * 学生证照片
     */
    @NotEmpty(message = "学生证必上传")
    private String studentCertificate;

    /**
     * 所在学校
     */
    @NotEmpty(message = "学校必填")
    private String studentSchool;
}