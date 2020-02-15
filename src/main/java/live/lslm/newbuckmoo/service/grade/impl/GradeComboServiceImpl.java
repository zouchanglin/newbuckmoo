package live.lslm.newbuckmoo.service.grade.impl;

import com.google.common.collect.Lists;
import live.lslm.newbuckmoo.entity.GradeCombo;
import live.lslm.newbuckmoo.repository.GradeComboRepository;
import live.lslm.newbuckmoo.service.grade.GradeComboService;
import live.lslm.newbuckmoo.vo.grade.GradeComboVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GradeComboServiceImpl implements GradeComboService {
    @Autowired
    private GradeComboRepository gradeComboRepository;

    @Override
    public GradeCombo getOneComboById(Integer gradeComboId) {
        return gradeComboRepository.getOne(gradeComboId);
    }

    @Override
    public void deleteOneCombo(Integer gradeComboId) {
        gradeComboRepository.deleteById(gradeComboId);
    }

    @Override
    public void updateOneCombo(GradeCombo gradeCombo) {
        gradeComboRepository.save(gradeCombo);
    }

    @Override
    public void addOneCombo(GradeCombo gradeCombo) {
        if(gradeCombo.getGradeId() == 0){
            GradeCombo save = new GradeCombo();
            save.setGradeName(gradeCombo.getGradeName());
            save.setGradeNum(gradeCombo.getGradeNum());
            save.setGradeMoney(gradeCombo.getGradeMoney());
            gradeComboRepository.save(save);
        }else{
            log.error("【新增积分购买套餐】 参数不正确");
        }
    }

    @Override
    public List<GradeComboVO> getAllGradeCombo() {
        List<GradeCombo> gradeComboList = gradeComboRepository.findAll();
        List<GradeComboVO> gradeComboVOList = Lists.newArrayListWithCapacity(gradeComboList.size());
        GradeComboVO gradeComboVO;
        for(GradeCombo gradeCombo: gradeComboList){
            gradeComboVO = new GradeComboVO();
            gradeComboVO.setGradeId(String.valueOf(gradeCombo.getGradeId()));
            gradeComboVO.setGradeMoney(String.valueOf(gradeCombo.getGradeMoney()));
            gradeComboVO.setGradeNum(String.valueOf(gradeCombo.getGradeNum()));
            gradeComboVO.setGradeName(gradeCombo.getGradeName());
            gradeComboVOList.add(gradeComboVO);
        }
        return gradeComboVOList;
    }
}
