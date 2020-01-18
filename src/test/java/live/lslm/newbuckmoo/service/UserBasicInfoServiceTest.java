package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.utils.KeyUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UserBasicInfoServiceTest {
    @Autowired
    private UserBasicInfoService userBasicInfoService;

    private final String OPENID = KeyUtil.genUniqueKey();
    @Before
    public void init(){
        UserBasicInfo userBasicInfo = new UserBasicInfo();
        userBasicInfo.setOpenId(OPENID);
        userBasicInfo.setUserPhone("123456789");
        userBasicInfo.setUserGrade(20);
        userBasicInfoService.updateOrCreateUserBasic(userBasicInfo);
    }

    @Test
    public void updateOrCreateUserBasic() {
        UserBasicInfo userBasicInfo = new UserBasicInfo();
        userBasicInfo.setOpenId(OPENID);
        userBasicInfo.setUserPhone("987654321");
        String userBasicOpenid = userBasicInfoService.updateOrCreateUserBasic(userBasicInfo);
        UserBasicInfo findRet = userBasicInfoService.getUserBasicInfoByOpenid(userBasicOpenid);
        assertEquals(20, findRet.getUserGrade().intValue());
    }

    @After
    public void clear(){
        userBasicInfoService.deleteOneUserBasicInfoById(OPENID);
    }
}