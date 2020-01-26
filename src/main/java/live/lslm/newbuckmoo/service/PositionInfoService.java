package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.entity.CategoryInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.form.PositionInfoForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
}