package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.form.StudentAttestationForm;

public interface StudentsInfoService {

    /**
     * 新增或者更新一条学生信息
     * @param studentAttestationForm 学生信息表单
     * @return 返回保存后的学生信息
     */
    StudentInfo createOrUpdateInfo(StudentAttestationForm studentAttestationForm);
}
