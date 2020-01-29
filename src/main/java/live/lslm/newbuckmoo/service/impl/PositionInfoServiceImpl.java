package live.lslm.newbuckmoo.service.impl;

import com.google.common.collect.Lists;
import live.lslm.newbuckmoo.dto.PositionInfoDTO;
import live.lslm.newbuckmoo.entity.ApplyPosition;
import live.lslm.newbuckmoo.entity.CategoryInfo;
import live.lslm.newbuckmoo.entity.PositionInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ClearingWayEnum;
import live.lslm.newbuckmoo.enums.ReadStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.PositionInfoForm;
import live.lslm.newbuckmoo.form.RequestByPageForm;
import live.lslm.newbuckmoo.form.StudentApplyPositionForm;
import live.lslm.newbuckmoo.repository.*;
import live.lslm.newbuckmoo.service.PositionInfoService;
import live.lslm.newbuckmoo.utils.ConstUtilPoll;
import live.lslm.newbuckmoo.utils.EnumUtil;
import live.lslm.newbuckmoo.utils.KeyUtil;
import live.lslm.newbuckmoo.vo.company.PositionForCompanyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class PositionInfoServiceImpl implements PositionInfoService {

    @Autowired
    private ApplyPositionRepository applyPositionRepository;

    @Autowired
    private PositionInfoRepository positionRepository;

    @Autowired
    private CompanyInfoRepository companyRepository;

    @Autowired
    private CategoryInfoRepository categoryRepository;

    @Autowired
    private UserBasicInfoRepository userBasicRepository;

    @Override
    public Page<PositionForCompanyVO> showMySelfCratedPosition(RequestByPageForm requestByPageForm) {
        String openId = requestByPageForm.getOpenId();
        PageRequest pageRequest = PageRequest.of(requestByPageForm.getPage(), requestByPageForm.getSize());
        Page<PositionInfo> infoPage = positionRepository.findAllByOpenId(openId, pageRequest);
        return convert(infoPage, pageRequest);
    }

    @Override
    public void applyPosition(StudentApplyPositionForm studentApplyPositionForm) {

        String openId = studentApplyPositionForm.getOpenId();
        String positionId = studentApplyPositionForm.getPositionId();
        ApplyPosition firstApply = applyPositionRepository.findFirstByOpenIdAndPositionId(openId, positionId);

        // 先判断有没有
        if(firstApply != null){
            log.error("【学生申请兼职】已申请过，请勿重复申请");
            throw new BuckmooException(ResultEnum.REPETITION_ERROR);
        }

        // 判断兼职是否存在+是否审核通过
        Optional<PositionInfo> positionInfoOpt = positionRepository.findById(positionId);
        if(positionInfoOpt.isPresent()){
            PositionInfo positionInfo = positionInfoOpt.get();
            if(!AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(positionInfo.getAuditStatus())){
                log.error("【学生申请兼职】兼职信息审核未通过");
                throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
            }
        }else{
            log.error("【学生申请兼职】兼职信息不存在");
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }

        //权限校验放在切面
        ApplyPosition applyPosition = new ApplyPosition();
        applyPosition.setReadStatus(ReadStatusEnum.NOT_READ.getCode());
        applyPosition.setOpenId(openId);
        applyPosition.setPositionId(positionId);
        applyPosition.setCreateTime(System.currentTimeMillis());
        applyPosition.setUpdateTime(System.currentTimeMillis());

        ApplyPosition saved = applyPositionRepository.save(applyPosition);
        log.info("【学生申请兼职信息】保存结果={}", saved);
    }

    @Override
    public Page<PositionInfoDTO> showPositionForStudent(RequestByPageForm requestByPageForm) {
        PageRequest pageRequest = PageRequest.of(requestByPageForm.getPage(), requestByPageForm.getSize());
        Page<PositionInfo> positionTop = positionRepository.findAllByAuditStatusOrderByPositionTop(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageRequest);

        List<PositionInfo> infoList = positionTop.getContent();
        List<PositionInfoDTO> infoDTOList = Lists.newArrayListWithCapacity(infoList.size());
        for(PositionInfo positionInfo: infoList){
            infoDTOList.add(convert(positionInfo));
        }
        return new PageImpl<>(infoDTOList, pageRequest, positionTop.getTotalElements());
    }

    @Override
    public PositionInfoDTO changeAuditStatus(String positionId, AuditStatusEnum auditStatusEnum, String remark) {
        Optional<PositionInfo> positionInfoOpt = positionRepository.findById(positionId);
        if(positionInfoOpt.isPresent()){
            PositionInfo positionInfo = positionInfoOpt.get();
            positionInfo.setAuditStatus(auditStatusEnum.getCode());
            positionInfo.setAuditRemark(remark);
            PositionInfo saved = positionRepository.save(positionInfo);
            log.info("【审核兼职信息】存储结果 {}", saved);
            return convert(saved);
        }else{
            log.error("【审核兼职信息】根据OPENID无法查找到兼职信息");
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public PositionInfoDTO getPositionById(String positionId) {
        Optional<PositionInfo> positionInfoOptional = positionRepository.findById(positionId);
        if(positionInfoOptional.isPresent()){
            return convert(positionInfoOptional.get());
        }else{
            log.error("【查找兼职详细信息为空】");
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public Page<PositionInfoDTO> getAllByNotAuditStatus(Integer auditStatus, Pageable pageable) {
        Page<PositionInfo> infoPage = positionRepository.findAllByAuditStatusIsNot(auditStatus, pageable);
        List<PositionInfo> infoList = infoPage.getContent();
        List<PositionInfoDTO> infoDTOList = Lists.newArrayListWithCapacity(infoList.size());
        for(PositionInfo positionInfo: infoList){
            infoDTOList.add(convert(positionInfo));
        }
        return new PageImpl<>(infoDTOList, pageable, infoPage.getTotalElements());
    }

    @Override
    public Page<PositionInfoDTO> getAllByAuditStatus(Integer auditStatus, Pageable pageable) {
        Page<PositionInfo> infoPage = positionRepository.findAllByAuditStatus(auditStatus, pageable);
        List<PositionInfo> infoList = infoPage.getContent();
        List<PositionInfoDTO> infoDTOList = Lists.newArrayListWithCapacity(infoList.size());
        for(PositionInfo positionInfo: infoList){
            infoDTOList.add(convert(positionInfo));
        }
        return new PageImpl<>(infoDTOList, pageable, infoPage.getTotalElements());
}

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
        positionInfoDTO.setCompanyInfo(companyRepository.findById(positionInfo.getOpenId()).orElse(null));
        positionInfoDTO.setUserBasicInfo(userBasicRepository.findById(positionInfo.getOpenId()).orElse(null));
        return positionInfoDTO;
    }

    private Page<PositionForCompanyVO> convert(Page<PositionInfo> positionInfos, PageRequest pageRequest){
        List<PositionInfo> content = positionInfos.getContent();
        List<PositionForCompanyVO> list = Lists.newArrayListWithCapacity(content.size());
        PositionForCompanyVO forCompanyVO;
        for(PositionInfo positionInfo: content){
            forCompanyVO = new PositionForCompanyVO();
            BeanUtils.copyProperties(positionInfo, forCompanyVO);
            forCompanyVO.setAuditStatusStr(Objects.requireNonNull(EnumUtil.getByCode(positionInfo.getAuditStatus(), AuditStatusEnum.class)).getMessage());
            forCompanyVO.setPositionClearingWayStr(Objects.requireNonNull(EnumUtil.getByCode(positionInfo.getPositionClearingWay(), ClearingWayEnum.class)).getMessage());

            //Category or Tags List
            String[] categoryStrList = positionInfo.getPositionCategory().split("#");
            Integer[] categoryNumList = new Integer[categoryStrList.length];
            CategoryInfo[] categoryInfoList = new CategoryInfo[categoryStrList.length];
            for (int i = 0; i < categoryStrList.length; i++) {
                categoryNumList[i] = Integer.parseInt(categoryStrList[i]);
                categoryInfoList[i] = categoryRepository.findById(categoryNumList[i]).orElse(null);
            }

            forCompanyVO.setCategoryList(categoryInfoList);
            forCompanyVO.setCreateTimeStr(ConstUtilPoll.dateFormat.format(new Date(positionInfo.getUpdateTime())));

            list.add(forCompanyVO);
        }
        return new PageImpl<>(list, pageRequest, positionInfos.getTotalElements());
    }
}