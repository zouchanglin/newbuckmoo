package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentInfoRepository extends JpaRepository<StudentInfo, String> {

}