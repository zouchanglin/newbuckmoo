package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGradeRepository extends JpaRepository<UserGrade, String> {
}