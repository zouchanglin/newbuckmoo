package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.StudentInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentInfoRepository extends JpaRepository<StudentInfo, String> {
    /**
     * 分页查找未通过审核的学生信息
     */
    Page<StudentInfo> findAllByAuditStatusIsNot(Integer notStatus, Pageable pageable);
}