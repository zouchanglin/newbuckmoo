package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.SchoolClubInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClubInfoRepository extends JpaRepository<SchoolClubInfo, String> {
    Page<SchoolClubInfo> findAllByAuditStatusIsNot(Integer auditStatus, Pageable pageable);
    Page<SchoolClubInfo> findAllByAuditStatus(Integer auditStatus, Pageable pageable);
}
