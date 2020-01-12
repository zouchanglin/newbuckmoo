package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.PositionInfo;
import live.lslm.newbuckmoo.enums.ClearingWayEnum;
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

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PositionInfoRepositoryTest {
    @Autowired
    private PositionInfoRepository repository;

    private final String KEY = KeyUtil.genUniqueKey();

    @Before
    public void init(){
        PositionInfo positionInfo = new PositionInfo();
        positionInfo.setPositionId(KEY);
        PositionInfo save = repository.save(positionInfo);
        assertNotNull(save);
    }

    @Test
    public void find(){
        Optional<PositionInfo> info = repository.findById(KEY);
        assertTrue(info.isPresent());
    }

    @After
    public void delete(){
        repository.deleteById(KEY);
        assertEquals(0, repository.findAll().size());
    }
}