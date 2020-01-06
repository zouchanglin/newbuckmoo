package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.dto.ClubApproveDTO;
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
}