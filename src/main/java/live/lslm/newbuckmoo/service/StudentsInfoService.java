package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.form.StudentAttestationForm;
import live.lslm.newbuckmoo.form.student.StudentRecommendSignForm;
import live.lslm.newbuckmoo.vo.StudentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentsInfoService extends UserInfoService{

    /**
     * 新增或者更新一条学生信息
     * @param studentAttestationForm 学生信息表单
     * @return 返回保存后的学生信息
     * @since 1.0
     */
    StudentApproveDTO createOrUpdateInfo(StudentAttestationForm studentAttestationForm);

    /**
     * 新增一条学生信息(推荐来的)
     * @param recommendSignForm 学生信息表单
     * @return 返回保存后的学生信息
     * @since 1.0
     */
    StudentApproveDTO createStudentInfoByRecommend(StudentRecommendSignForm recommendSignForm);

    /**
     * 分页获取学生认证请求列表
     * @param pageable 分页参数
     * @return 分页查询结果
     * @since 1.0
     */
    Page<StudentApproveDTO> getApproveList(Pageable pageable);

    /**
     * 分页获取学生列表
     * @param pageable 分页参数
     * @return 分页查询结果
     * @since 1.0
     */
    Page<StudentApproveDTO> getStudentList(Pageable pageable);

    /**
     * 学生认证通过
     * @param openId 学生微信Id
     * @param auditRemark 审核意见
     * @since 1.1
     */
    StudentApproveDTO passStudentApprove(String openId, String auditRemark);

    /**
     * 学生认证驳回
     * @param openId 学生微信Id
     * @param auditRemark 审核意见
     * @since 1.1
     */
    StudentApproveDTO rejectedStudentApprove(String openId, String auditRemark);

    /**
     * 根据OpenId查询学信息
     * @param openId 学生微信Id
     * @return 查询结果DTO对象
     * @since 1.0
     */
    StudentApproveDTO getStudentInfoByOpenId(String openId);

    /**
     * DTO转VO对象
     * @param studentApproveDTO DTO对象
     * @return VO对象
     * @since 1.0
     */
    StudentVO convertToVO(StudentApproveDTO studentApproveDTO);

    /**
     * 根据OpenID获取VO对象
     * @param openId openID
     * @return VO对象
     * @since 1.0
     */
    StudentVO getStudentVOByOpenId(String openId);
}