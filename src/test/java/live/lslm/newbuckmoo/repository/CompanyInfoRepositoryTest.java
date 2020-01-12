package live.lslm.newbuckmoo.repository;



import live.lslm.newbuckmoo.entity.CompanyInfo;
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
import static org.junit.Assert.assertNotNull;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyInfoRepositoryTest {
    @Autowired
    private CompanyInfoRepository repository;

    private final String OPEN_ID =  KeyUtil.genUniqueKey();
    private final String COMPANY_ID =  KeyUtil.genUniqueKey();

    @Before
    public void save(){
        CompanyInfo companyInfo = new CompanyInfo();
        companyInfo.setOpenId(OPEN_ID);
        companyInfo.setCompanyId(COMPANY_ID);
        CompanyInfo save = repository.save(companyInfo);
        assertNotNull(save);
    }

    @Test
    public void find(){
        Optional<CompanyInfo> companyInfo = repository.findById(OPEN_ID);
        assertTrue(companyInfo.isPresent());
        CompanyInfo companyInfoRet = companyInfo.get();
        assertNotNull(companyInfoRet);
    }

    @After
    public void updateAndDelete(){
        Optional<CompanyInfo> companyInfo = repository.findById(OPEN_ID);
        if(companyInfo.isPresent()){
            CompanyInfo companyInfoRet = companyInfo.get();
            CompanyInfo save = repository.save(companyInfoRet);
            assertNotNull(save);
        }
        repository.deleteById(OPEN_ID);

        Optional<CompanyInfo> find = repository.findById(OPEN_ID);
        assertFalse(find.isPresent());
    }

}