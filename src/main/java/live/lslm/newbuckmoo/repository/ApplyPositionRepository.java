package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.ApplyPosition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyPositionRepository extends JpaRepository<ApplyPosition, Integer> {
    ApplyPosition findFirstByOpenIdAndPositionId(String openId, String positionId);

    Page<ApplyPosition> findAllByPositionId(String positionId, Pageable pageable);
}