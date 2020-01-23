package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.entity.CategoryInfo;
import live.lslm.newbuckmoo.entity.PositionInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.PositionInfoForm;
import live.lslm.newbuckmoo.repository.CategoryInfoRepository;
import live.lslm.newbuckmoo.repository.PositionInfoRepository;
import live.lslm.newbuckmoo.repository.UserBasicInfoRepository;
import live.lslm.newbuckmoo.service.PositionInfoService;
import live.lslm.newbuckmoo.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class PositionInfoServiceImpl implements PositionInfoService {
    @Autowired
    private PositionInfoRepository positionRepository;

    @Autowired
    private CategoryInfoRepository categoryRepository;

    @Autowired
    private UserBasicInfoRepository userBasicRepository;


    @Override
    public List<CategoryInfo> getAllCategoryInfo() {
        return categoryRepository.findAll();
    }

    @Override
    public PositionInfoDTO createOrUpdatePosition(PositionInfoForm positionInfoForm) {
        PositionInfo positionInfo;
        if(StringUtils.isEmpty(positionInfoForm.getPositionId())){
            //新增表单
            positionInfo = new PositionInfo();
            BeanUtils.copyProperties(positionInfoForm, positionInfo);
            positionInfo.setCreateTime(System.currentTimeMillis());
            positionInfo.setPositionBrowse(0L);
            positionInfo.setPositionId(KeyUtil.genUniqueKey());
        }else{
            Optional<PositionInfo> positionInfoOpt = positionRepository.findById(positionInfoForm.getPositionId());
            if(!positionInfoOpt.isPresent())  throw new BuckmooException(ResultEnum.PARAM_ERROR);
            positionInfo = positionInfoOpt.get();
            BeanUtils.copyProperties(positionInfoForm, positionInfo);
        }
        //分类标签的处理
        Integer[] categoryNumList = positionInfoForm.getPositionCategory();
        StringBuilder sb = new StringBuilder();
        for (Integer categoryNum: categoryNumList) {
            sb.append(categoryNum).append("#");
        }
        positionInfo.setPositionCategory(sb.toString());
        positionInfo.setUpdateTime(System.currentTimeMillis());
        positionInfo.setAuditStatus(AuditStatusEnum.AUDIT_RUNNING.getCode());

        return convert(positionRepository.save(positionInfo));
    }

    private PositionInfoDTO convert(PositionInfo positionInfo){
        PositionInfoDTO positionInfoDTO = new PositionInfoDTO();
        BeanUtils.copyProperties(positionInfo, positionInfoDTO);

        //Category or Tags List
        String[] categoryStrList = positionInfo.getPositionCategory().split("#");
        Integer[] categoryNumList = new Integer[categoryStrList.length];
        CategoryInfo[] categoryInfoList = new CategoryInfo[categoryStrList.length];
        for (int i = 0; i < categoryStrList.length; i++) {
            categoryNumList[i] = Integer.parseInt(categoryStrList[i]);
            categoryInfoList[i] = categoryRepository.findById(categoryNumList[i]).orElse(null);
        }
        positionInfoDTO.setPositionCategory(categoryNumList);
        positionInfoDTO.setCategoryList(categoryInfoList);

        positionInfoDTO.setUserBasicInfo(userBasicRepository.findById(positionInfo.getOpenId()).orElse(null));
        return positionInfoDTO;
    }
}