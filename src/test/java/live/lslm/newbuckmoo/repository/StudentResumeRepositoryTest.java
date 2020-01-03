package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.StudentResume;
import live.lslm.newbuckmoo.utils.KeyUtil;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StudentResumeRepositoryTest {
    @Autowired
    private StudentResumeRepository repository;
    private final String OPEN_ID = KeyUtil.genUniqueKey();

    @Before
    public void init(){
        StudentResume studentResume = new StudentResume();
        studentResume.setOpenId(OPEN_ID);
        StudentResume save = repository.save(studentResume);
        assertNotNull(save);
    }

    @Test
    public void find(){
        Optional<StudentResume> byId = repository.findById(OPEN_ID);
        assertTrue(byId.isPresent());
        StudentResume studentResume = byId.get();
        studentResume.setResumeName("xxx");
        StudentResume save = repository.save(studentResume);
        log.info("save={}", save);
        assertNotNull(save);
    }

    @After
    public void delete(){
        repository.deleteById(OPEN_ID);
    }
}