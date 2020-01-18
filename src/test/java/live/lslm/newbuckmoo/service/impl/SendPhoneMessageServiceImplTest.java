package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.service.SendPhoneMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SendPhoneMessageServiceImplTest {
    @Autowired
    private SendPhoneMessageService service;
    @Test
    public void sendMsg() {
        service.sendMsg("15291418231", "123456");
    }
}