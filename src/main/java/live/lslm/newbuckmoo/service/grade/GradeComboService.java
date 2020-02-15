package live.lslm.newbuckmoo.service.grade;

import live.lslm.newbuckmoo.entity.GradeCombo;
import live.lslm.newbuckmoo.vo.grade.GradeComboVO;

import java.util.List;

public interface GradeComboService {
    List<GradeComboVO> getAllGradeCombo();

    void updateOneCombo(GradeCombo gradeCombo);

    void addOneCombo(GradeCombo gradeCombo);

    void deleteOneCombo(Integer gradeComboId);
}