package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.UserBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBasicInfoRepository extends JpaRepository<UserBasicInfo, String> {
}
