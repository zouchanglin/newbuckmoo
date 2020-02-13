package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.RecommendSign;
import live.lslm.newbuckmoo.enums.RecommendTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
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
public class RecommendSignRepositoryTest {
    private final Integer KEY = 1;

    @Autowired
    private RecommendSignRepository recommendSignRepository;

    @Before
    public void init(){
        RecommendSign recommendSign = new RecommendSign();
        recommendSign.setRecommendId(KEY);
        recommendSign.setPushOpenId("AAA");
        recommendSign.setSignOpenId("BBB");
        recommendSign.setRecommendType(RecommendTypeEnum.COMPANY_RECOMMEND.getCode());
        recommendSignRepository.save(recommendSign);
    }
    @Test
    public void find(){
        assertNotNull(recommendSignRepository.getOne(KEY));
    }

    @After
    public void delete(){
        recommendSignRepository.deleteById(KEY);
        assertFalse(recommendSignRepository.findById(KEY).isPresent());
    }
}