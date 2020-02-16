package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.GeneralOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneralOrderRepository extends JpaRepository<GeneralOrder, String> {
    List<GeneralOrder> findAllByOrderOpenIdOrderByCreateTime(String orderOpenId);
}