package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.UserBasicInfo;
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
public class UserBasicInfoRepositoryTest {
    @Autowired
    private UserBasicInfoRepository repository;

    private final String OPEN_ID = KeyUtil.genUniqueKey();

    @Before
    public void init(){
        UserBasicInfo userBasicInfo = new UserBasicInfo();
        userBasicInfo.setOpenId(OPEN_ID);
        userBasicInfo.setUserCity("西安");
        userBasicInfo.setUserIcon("http://xxx.png");
        userBasicInfo.setUserName("Tim");

        UserBasicInfo save = repository.save(userBasicInfo);
        assertNotNull(save);
        userBasicInfo.setUserPhone("12345612345");
        UserBasicInfo saveRet = repository.save(userBasicInfo);
        assertNotNull(saveRet);
    }

    @Test
    public void find(){
        Optional<UserBasicInfo> userBasicInfoRet = repository.findById(OPEN_ID);
        assertTrue(userBasicInfoRet.isPresent());
    }

    @After
    public void delete(){
        repository.deleteById(OPEN_ID);
        Optional<UserBasicInfo> userBasicInfoRet = repository.findById(OPEN_ID);
        assertFalse(userBasicInfoRet.isPresent());
    }
}