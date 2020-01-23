package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.entity.CategoryInfo;
import live.lslm.newbuckmoo.form.PositionInfoForm;

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
}