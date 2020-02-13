package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.dto.ClubApproveDTO;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.form.SchoolClubAttestationForm;
import live.lslm.newbuckmoo.form.club.ClubRecommendSignForm;
import live.lslm.newbuckmoo.vo.SchoolClubVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolClubInfoService extends UserInfoService{
    /**
     * 分页查询社团未审核与审核未通过的数据
     * @param pageable 分页参数
     * @return 查询结果
     */
    Page<ClubApproveDTO> getApproveList(Pageable pageable);

    /**
     * 创建或者修改社团信息
     * @param schoolClubForm 社团信息表单
     * @return 保存后的信息
     */
    ClubApproveDTO createOrUpdateInfo(SchoolClubAttestationForm schoolClubForm);

    /**
     * 创建社团信息
     * @param recommendSignForm 社团信息表单
     * @return 保存后的信息
     */
    ClubApproveDTO createStudentInfoByRecommend(ClubRecommendSignForm recommendSignForm);

    /**
     * 页查询社团审核通过的数据
     * @param pageable 分页参数
     * @return 分页查询结果
     */
    Page<ClubApproveDTO> getClubList(Pageable pageable);

    /**
     * 根据OpenId查询社团信息
     * @param openId openId
     * @return 查询结果DTO对象
     */
    ClubApproveDTO getClubInfoByOpenId(String openId);

    /**
     * 根据openID获取VO对象
     * @param openId openID
     * @return VO对象
     */
    SchoolClubVO getClubVOByOpenId(String openId);

    /**
     * 修改社团审核结果
     * @param openId openId
     * @param auditSuccess 修改审核结果
     * @param auditRemark 审核备注
     */
    ClubApproveDTO changeClubApprove(String openId, AuditStatusEnum auditSuccess, String auditRemark);


}