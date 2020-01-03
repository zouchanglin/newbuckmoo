package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.StudentInfo;
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
public class StudentInfoRepositoryTest {
    @Autowired
    private StudentInfoRepository repository;

    private final String KEY =  KeyUtil.genUniqueKey();

    @Before
    public void save(){
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setOpenId(KEY);
        StudentInfo save = repository.save(studentInfo);
        assertNotNull(save);
    }

    @Test
    public void find(){
        Optional<StudentInfo> studentInfo = repository.findById(KEY);
        assertTrue(studentInfo.isPresent());
        StudentInfo studentInfoRet = studentInfo.get();
        assertNotNull(studentInfoRet);
    }

    @After
    public void updateAndDelete(){
        Optional<StudentInfo> studentInfo = repository.findById(KEY);
        if(studentInfo.isPresent()){
            StudentInfo studentInfoRet = studentInfo.get();
            StudentInfo save = repository.save(studentInfoRet);
            assertNotNull(save);
        }
        repository.deleteById(KEY);

        Optional<StudentInfo> find = repository.findById(KEY);
        assertFalse(find.isPresent());
    }
}