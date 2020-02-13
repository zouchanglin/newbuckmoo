package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.RecommendSign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendSignRepository extends JpaRepository<RecommendSign, Integer> {
    RecommendSign findFirstBySignOpenIdAndRecommendType(String openId, Integer type);
}