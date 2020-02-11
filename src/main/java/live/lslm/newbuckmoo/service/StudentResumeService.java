package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.dto.StudentResumeDTO;
import live.lslm.newbuckmoo.form.StudentResumeForm;
import live.lslm.newbuckmoo.vo.StudentResumeVO;

public interface StudentResumeService {
    /**
     * 学生创建或者更新简历
     * @param studentResumeForm 简历信息表单
     * @return DTO
     */
    StudentResumeDTO createOrUpdateResume(StudentResumeForm studentResumeForm);

    /**
     * 获取学生的简历信息 VO
     * @param openId openID
     * @return 简历信息 VO
     */
    StudentResumeVO getOneResumeByOpenId(String openId);
    /**
     * 获取学生的简历信息 VO
     * @param studentId 学生学号
     * @return 简历信息 VO
     */
    StudentResumeVO getOneResumeByStudentId(String studentId);
}