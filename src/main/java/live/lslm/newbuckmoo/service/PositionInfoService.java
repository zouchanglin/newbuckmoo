package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.entity.CategoryInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.form.*;
import live.lslm.newbuckmoo.vo.StudentVO;
import live.lslm.newbuckmoo.vo.company.PositionForCompanyVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PositionInfoService {
    /**
     * 创建或者更新一条兼职信息
     * @param positionInfoForm 兼职信息的表单
     * @return 兼职信息的DTO对象
     */
    PositionInfoDTO createOrUpdatePosition(PositionInfoForm positionInfoForm);

    /**
     * 获取所有标签信息
     * @return 标签集合
     */
    List<CategoryInfo> getAllCategoryInfo();

    /**
     * @param code 根据审核状态获取兼职信息的分页查询结果
     * @param pageable 分页参数
     * @return 分页查询结果
     */
    Page<PositionInfoDTO> getAllByAuditStatus(Integer code, Pageable pageable);

    /**
     * 根据审核状态获取其他状态的兼职信息的分页查询结果
     */
    Page<PositionInfoDTO> getAllByNotAuditStatus(Integer code, Pageable pageable);

    /**
     * 根据兼职状态Id获取兼职信息详细信息
     * @param positionId 兼职信息的Id
     * @return 兼职信息DTO对象
     */
    PositionInfoDTO getPositionById(String positionId);

    /**
     * 改变兼职信息审核状态
     * @param auditStatusEnum 审核状态
     */
    PositionInfoDTO changeAuditStatus(String positionId, AuditStatusEnum auditStatusEnum, String remark);

    /**
     * 给学生用户展示兼职信息
     * @param requestByPageForm 请求表单数据
     * @return DTO对象
     */
    Page<PositionInfoDTO> showPositionForStudent(RequestByPageForm requestByPageForm);

    /**
     * 学生用户申请兼职信息
     * @param studentApplyPositionForm 申请表单
     */
    void applyPosition(StudentApplyPositionForm studentApplyPositionForm);

    /**
     * 企业用户查看自己发布的兼职
     * @param requestByPageForm 请求表单数据
     * @return VO Page
     */
    Page<PositionForCompanyVO> showMySelfCratedPosition(RequestByPageForm requestByPageForm);

    /**
     * 企业用户查看自己发布的某一条兼职的申请人列表
     */
    Page<StudentVO> showMyPositionApply(ShowPositionApplyFrom showPositionApplyFrom);

    
    /**
     * 分类给学生用户展示兼职信息
     * @param requestByPageForm 请求表单数据
     * @return DTO对象
     */
    Page<PositionInfoDTO> showPositionForStudentByTag(PositionListRequestByPageForm requestByPageForm);

    /**
     * 增加兼职信息浏览量
     * @param positionId 兼职信息ID
     */
    void addPositionBrowse(String positionId);
}