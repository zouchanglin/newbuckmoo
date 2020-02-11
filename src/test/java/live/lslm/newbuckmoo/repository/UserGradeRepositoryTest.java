package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.UserGrade;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserGradeRepositoryTest {

    private final String OPEN_ID = "oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk";

    @Autowired
    private UserGradeRepository userGradeRepository;

    @Before
    public void init(){
        UserGrade userGrade = new UserGrade();
        userGrade.setClubGrade(0);
        userGrade.setCompanyGrade(0);
        userGrade.setStudentGrade(0);
        userGrade.setOpenId(OPEN_ID);
        userGradeRepository.save(userGrade);
    }

    @Test
    public void find(){
        assertTrue(userGradeRepository.findById(OPEN_ID).isPresent());
    }

    @After
    public void delete(){
        userGradeRepository.deleteById(OPEN_ID);
        assertFalse(userGradeRepository.findById(OPEN_ID).isPresent());
    }
}