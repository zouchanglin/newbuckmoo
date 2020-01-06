package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.SchoolClubInfo;
import live.lslm.newbuckmoo.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SchoolClubInfoRepositoryTest {
    @Autowired
    private SchoolClubInfoRepository schoolClubInfoRepository;

    private final String key =  KeyUtil.genUniqueKey();

    @Test
    public void save(){
        SchoolClubInfo schoolClubInfo = new SchoolClubInfo();
        schoolClubInfo.setOpenId(key);
        assertNotNull(schoolClubInfoRepository.save(schoolClubInfo));
    }

    @After
    public void delete(){
        schoolClubInfoRepository.deleteById(key);
        Optional<SchoolClubInfo> byId = schoolClubInfoRepository.findById(key);
        assertFalse(byId.isPresent());
    }
}