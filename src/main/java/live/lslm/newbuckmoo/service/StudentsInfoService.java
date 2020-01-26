package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.form.StudentAttestationForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentsInfoService {

    /**
     * 新增或者更新一条学生信息
     * @param studentAttestationForm 学生信息表单
     * @return 返回保存后的学生信息
     */
    StudentApproveDTO createOrUpdateInfo(StudentAttestationForm studentAttestationForm);

    /**
     * 分页获取学生认证请求列表
     * @param pageable 分页参数
     * @return 分页查询结果
     */
    Page<StudentApproveDTO> getApproveList(Pageable pageable);

    /**
     * 分页获取学生列表
     * @param pageable 分页参数
     * @return 分页查询结果
     */
    Page<StudentApproveDTO> getStudentList(Pageable pageable);

    /**
     * 学生认证通过
     * @param openid 学生微信Id
     */
    StudentApproveDTO passStudentApprove(String openid);

    /**
     * 学生认证驳回
     * @param openid 学生微信Id
     */
    StudentApproveDTO rejectedStudentApprove(String openid);

    /**
     * 根据OpenId查询学信息
     * @param openId 学生微信Id
     * @return 查询结果DTO对象
     */
    StudentApproveDTO getStudentInfoByOpenId(String openId);
}
