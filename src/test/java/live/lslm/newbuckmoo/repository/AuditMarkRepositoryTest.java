package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.dto.AuditMarkDTO;
import live.lslm.newbuckmoo.entity.AuditMark;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuditMarkRepositoryTest {

    @Autowired
    private AuditMarkRepository auditMarkRepository;

    private final String OPENID = "xxxxxxxxxxxxx";
    @Test
    public void save(){
        AuditMark auditMark = new AuditMark();
        AuditMarkDTO auditMarkDTO = AuditMarkDTO.getInitInstance();
        auditMarkDTO.setOpenId(OPENID);
        BeanUtils.copyProperties(auditMarkDTO, auditMark);
        assertNotNull(auditMarkRepository.save(auditMark));
    }

    @After
    public void delete(){
        auditMarkRepository.deleteById(OPENID);
        assertEquals(0, auditMarkRepository.findAll().size());
    }
}