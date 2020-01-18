package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentInfoRepositoryTest_02 {
    @Autowired
    private StudentInfoRepository repository;

    @Before
    public void save(){
        for (int i = 1; i <= 10; i++) {
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setOpenId(KeyUtil.genUniqueKey());
            if(i % 3 == 0){
                studentInfo.setAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode());
            }
            StudentInfo save = repository.save(studentInfo);
            assertNotNull(save);
        }
    }

    @Test
    public void findByAuditForPage(){
        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<StudentInfo> studentInfos = repository.findAllByAuditStatusIsNot(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageRequest);
        assertEquals(studentInfos.getContent().size(), 7);
    }


    @After
    public void updateAndDelete(){
        repository.deleteAll();
    }
}