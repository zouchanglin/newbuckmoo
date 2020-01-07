package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.dto.ClubApproveDTO;
import live.lslm.newbuckmoo.entity.SchoolClubInfo;
import live.lslm.newbuckmoo.form.SchoolClubForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface SchoolClubInfoService {
    /**
     * 分页查询社团未审核与审核未通过的数据
     * @param pageable 分页参数
     * @return 查询结果
     */
    Page<ClubApproveDTO> getApproveList(Pageable pageable);

    /**
     * 修改社团审核结果
     * @param openid openId
     * @param code 修改审核结果
     */
    void changeClubApprove(String openid, Integer code);

    /**
     * 创建或者修改社团信息
     * @param schoolClubForm 社团信息表单
     * @return 保存后的信息
     */
    SchoolClubInfo createOrUpdateInfo(SchoolClubForm schoolClubForm);

    /**
     * 页查询社团审核通过的数据
     * @param pageable 分页参数
     * @return 分页查询结果
     */
    Page<ClubApproveDTO> getClubList(Pageable pageable);
}