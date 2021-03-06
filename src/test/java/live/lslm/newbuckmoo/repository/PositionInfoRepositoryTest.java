package live.lslm.newbuckmoo.repository;

import live.lslm.newbuckmoo.entity.PositionInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
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
        positionInfo.setCreateTime(System.currentTimeMillis());
        positionInfo.setUpdateTime(System.currentTimeMillis());
        positionInfo.setPositionCategory("1#4#8#9");
        PositionInfo save = repository.save(positionInfo);
        assertNotNull(save);
    }

    @Test
    public void find2(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<PositionInfo> contains = repository.findAllByAuditStatusAndPositionCategoryContains(
                AuditStatusEnum.AUDIT_SUCCESS.getCode(),
                "1", pageRequest);
        List<PositionInfo> content = contains.getContent();
        for(PositionInfo positionInfo: content){
            log.info("【复杂查询】{}", positionInfo);
        }
    }

    @Test
    public void find(){
        Optional<PositionInfo> info = repository.findById(KEY);
        assertTrue(info.isPresent());
    }

    @Test
    public void findByStatus(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<PositionInfo> allByAuditStatus = repository.findAllByAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageRequest);
        assertNotNull(allByAuditStatus);
    }

    @Test
    public void findAllByAuditStatusOrderByPositionTop(){
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<PositionInfo> positionTop = repository.findAllByAuditStatusOrderByPositionTop(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageRequest);
        List<PositionInfo> content = positionTop.getContent();
        for (PositionInfo positionInfo: content){
            log.info("【测试遍历数据】 position={}", positionInfo);
        }
    }

    @After
    public void delete(){
        repository.deleteById(KEY);
    }
}