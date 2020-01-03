package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.StudentResume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentResumeRepository extends JpaRepository<StudentResume, String> {
}