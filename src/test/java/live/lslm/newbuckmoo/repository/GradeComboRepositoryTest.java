package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.GradeCombo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GradeComboRepositoryTest {

    @Autowired
    private GradeComboRepository gradeComboRepository;

    @Before
    public void init(){
        gradeComboRepository.deleteAll();
        GradeCombo gradeCombo = new GradeCombo();
        gradeCombo.setGradeMoney(300);
        gradeCombo.setGradeNum(600);
        gradeCombo.setGradeName("300充600积分");
        gradeComboRepository.save(gradeCombo);
    }

    @Test
    public void find(){
        GradeCombo combo = gradeComboRepository.getOne(1);
        assertNotNull(combo);
    }

    @After
    public void delete(){
        gradeComboRepository.deleteAll();
        assertEquals(0, gradeComboRepository.findAll().size());
    }
}