package live.lslm.newbuckmoo.service.grade;

import live.lslm.newbuckmoo.entity.GradeCombo;
import live.lslm.newbuckmoo.vo.grade.GradeComboVO;

import java.util.List;

public interface GradeComboService {
    /* 获取所有套餐 */
    List<GradeComboVO> getAllGradeCombo();

    /* 更新一条套餐 */
    void updateOneCombo(GradeCombo gradeCombo);

    /* 增加一条积分套餐 */
    void addOneCombo(GradeCombo gradeCombo);

    /* 删除一条积分套餐 */
    void deleteOneCombo(Integer gradeComboId);
    /* 根据Id获取积分套餐 */
    GradeCombo getOneComboById(Integer gradeComboId);
}