package live.lslm.newbuckmoo.repository;


import live.lslm.newbuckmoo.entity.CategoryInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryInfoRepositoryTest {
    @Autowired
    private CategoryInfoRepository repository;

    @Before
    public void init(){
        repository.deleteAll();
        String[] nameList = {"附近兼职", "线上兼职", "高薪兼职",
                "福利岗位", "日结专区", "寒假兼职",
                "暑假兼职", "名企专区", "校园代理",
                "礼仪模特", "家教助教", "义工旅行"};
        for (String name: nameList){
            CategoryInfo categoryInfo = new CategoryInfo();
            categoryInfo.setCategoryName(name);
            CategoryInfo save = repository.save(categoryInfo);
            assertNotNull(save);
        }
    }

    @Test
    public void findAll(){
        List<CategoryInfo> list = repository.findAll();
        assertEquals(list.size(), 12);
    }

    @Test
    public void delete(){
        repository.deleteAll();
        List<CategoryInfo> list = repository.findAll();
        assertEquals(list.size(), 0);
    }
}