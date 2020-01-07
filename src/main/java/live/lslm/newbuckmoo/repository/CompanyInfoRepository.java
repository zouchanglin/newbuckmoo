package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.CompanyInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyInfoRepository extends JpaRepository<CompanyInfo, String> {
    Page<CompanyInfo> findAllByAuditStatusIsNot(Integer status, Pageable pageable);

    Page<CompanyInfo> findAllByAuditStatus(Integer status, Pageable pageable);
}