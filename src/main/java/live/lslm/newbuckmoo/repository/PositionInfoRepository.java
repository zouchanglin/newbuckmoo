package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.PositionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionInfoRepository extends JpaRepository<PositionInfo, String> {
    Page<PositionInfo> findAllByAuditStatus(Integer auditStatus, Pageable pageable);

    Page<PositionInfo> findAllByAuditStatusIsNot(Integer auditStatus, Pageable pageable);

    Page<PositionInfo> findAllByAuditStatusOrderByPositionTop(Integer auditStatus, Pageable pageable);

    Page<PositionInfo> findAllByOpenId(String openId, Pageable pageable);
}