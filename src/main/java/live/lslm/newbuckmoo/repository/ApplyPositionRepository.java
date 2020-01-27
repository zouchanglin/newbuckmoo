package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.ApplyPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyPositionRepository extends JpaRepository<ApplyPosition, Integer> {
    ApplyPosition findFirstByOpenIdAndPositionId(String openId, String positionId);
}