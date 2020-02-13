package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.LongTextStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LongTextStorageRepository extends JpaRepository<LongTextStorage, String> {
}
