package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.ApplyPosition;
import live.lslm.newbuckmoo.enums.ReadStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplyPositionRepositoryTest {
    @Autowired
    private ApplyPositionRepository repository;

    @Before
    public void init(){
        ApplyPosition applyPosition = new ApplyPosition();
        applyPosition.setPositionId("111");
        applyPosition.setCreateTime(System.currentTimeMillis());
        applyPosition.setUpdateTime(System.currentTimeMillis());
        applyPosition.setOpenId("oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk2");
        applyPosition.setReadStatus(ReadStatusEnum.READ.getCode());
        assertNotNull(repository.save(applyPosition));
    }

    @Test
    public void find(){
        assertEquals(repository.findAll().size(), 1);
    }

    @Test
    public void findFirstByOpenIdAndPositionId(){
        ApplyPosition first = repository.findFirstByOpenIdAndPositionId("oxrwq0zPbgTB-gV9Y4Q-hN4g25Fk2", "111");
        log.info("【查找结果】first={}", first);
    }
}