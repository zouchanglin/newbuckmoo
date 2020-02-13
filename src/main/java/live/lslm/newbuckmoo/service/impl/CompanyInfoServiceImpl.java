package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.convert.CompanyFormToInfoConvert;
import live.lslm.newbuckmoo.convert.CompanyInfoToApproveConvert;
import live.lslm.newbuckmoo.dto.AuditMarkDTO;
import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.entity.AuditMark;
import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.entity.RecommendSign;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.RecommendTypeEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.CompanyAttestationForm;
import live.lslm.newbuckmoo.form.company.CompanyRecommendSignForm;
import live.lslm.newbuckmoo.repository.AuditMarkRepository;
import live.lslm.newbuckmoo.repository.CompanyInfoRepository;
import live.lslm.newbuckmoo.repository.RecommendSignRepository;
import live.lslm.newbuckmoo.repository.SystemSettingsRepository;
import live.lslm.newbuckmoo.service.CompanyInfoService;
import live.lslm.newbuckmoo.service.UserBasicInfoService;
import live.lslm.newbuckmoo.service.WebSocket;
import live.lslm.newbuckmoo.service.WechatPushMessageService;
import live.lslm.newbuckmoo.utils.EnumUtil;
import live.lslm.newbuckmoo.vo.CompanyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class CompanyInfoServiceImpl implements CompanyInfoService {
    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    @Autowired
    private AuditMarkRepository auditMarkRepository;

    @Autowired
    private RecommendSignRepository recommendSignRepository;

    @Autowired
    private WechatPushMessageService wechatPushMessageService;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private SystemSettingsRepository settingsRepository;


    @Override
    public CompanyVO getCompanyVOByOpenId(String openId) {
        Optional<CompanyInfo> companyInfoOpt = companyInfoRepository.findById(openId);
        if(companyInfoOpt.isPresent()){
            CompanyInfo companyInfo = companyInfoOpt.get();
            CompanyVO companyVO = new CompanyVO();
            BeanUtils.copyProperties(companyInfo, companyVO);
            companyVO.setAuditStatusStr(
                    Objects.requireNonNull(EnumUtil.getByCode(companyVO.getAuditStatus(), AuditStatusEnum.class))
                    .getMessage());
            return companyVO;
        }
        return null;
    }

    @Override
    public CompanyApproveDTO getCompanyByOpenId(String openId) {
        Optional<CompanyInfo> companyInfo = companyInfoRepository.findById(openId);
        return companyInfo.map(this::convert).orElse(null);
    }

    @Override
    public Page<CompanyApproveDTO> getCompanyList(Pageable pageable) {
        Page<CompanyInfo> companyInfoPage = companyInfoRepository
                .findAllByAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageable);
        List<CompanyApproveDTO> dTOList = CompanyInfoToApproveConvert.convert(companyInfoPage.getContent());
        for(CompanyApproveDTO companyApproveDTO: dTOList){
            companyApproveDTO.setUserBasicInfo(userBasicInfoService.getUserBasicInfoByOpenid(companyApproveDTO.getOpenId()));
        }
        return new PageImpl<>(dTOList, pageable, companyInfoPage.getTotalElements());
    }
    @Override
    public CompanyApproveDTO changeCompanyApprove(String openId, AuditStatusEnum auditSuccess, String auditRemark) {
        Integer code = auditSuccess.getCode();
        Optional<CompanyInfo> findResult = companyInfoRepository.findById(openId);
        if(findResult.isPresent()){
            CompanyInfo companyInfo = findResult.get();
            if(companyInfo.getAuditStatus().equals(code)){
                throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
            }else{
                if(EnumUtil.getByCode(code, AuditStatusEnum.class) == null){
                    throw new BuckmooException(ResultEnum.PARAM_ERROR);
                }
                companyInfo.setAuditStatus(code);
                companyInfo.setUpdateTime(System.currentTimeMillis());
                CompanyInfo save = companyInfoRepository.save(companyInfo);
                log.info("【企业信息审核】 保存结果{}", save);
                saveAuditRemark(openId, auditRemark);
                return convert(save);
            }
        }else {
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }
    private void saveAuditRemark(String openId, String auditRemark) {
        Optional<AuditMark> auditMarkOpt = auditMarkRepository.findById(openId);
        AuditMark auditMark = new AuditMark();
        if (auditMarkOpt.isPresent()) {
            BeanUtils.copyProperties(auditMarkOpt.get(), auditMark);
        } else {
            auditMark = AuditMarkDTO.getAuditMarkInstance();
            auditMark.setOpenId(openId);
        }
        auditMark.setAuditCompanyTime(System.currentTimeMillis());
        auditMark.setAuditCompanyCount(auditMark.getAuditCompanyCount() + 1);
        auditMark.setCompanyMark(auditRemark);
        AuditMark save = auditMarkRepository.save(auditMark);
        log.info("【企业审核意见存档】 {}", save);
    }


    @Override
    public CompanyApproveDTO createOrUpdateInfo(CompanyAttestationForm companyAttestationForm) {
        Optional<CompanyInfo> companyInfoFindRet = companyInfoRepository.findById(companyAttestationForm.getOpenId());
        CompanyInfo saveCompany;
        if (companyInfoFindRet.isPresent()) {
            saveCompany = companyInfoFindRet.get();
            //数据库中存在，但是处于未通过状态才可以继续提交
            //校验是否处于正在审核的状态，未通过审核时才能继续提交
            if(AuditStatusEnum.AUDIT_RUNNING.getCode().equals(saveCompany.getAuditStatus())){
                throw new BuckmooException(ResultEnum.AUDITING_NOT_ALLOWED);
            }
        } else {
            saveCompany = new CompanyInfo();
            saveCompany.setOpenId(companyAttestationForm.getOpenId());
        }
        CompanyFormToInfoConvert.formToCompany(companyAttestationForm, saveCompany);
        saveCompany.setUpdateTime(System.currentTimeMillis());
        saveCompany.setAuditStatus(AuditStatusEnum.AUDIT_RUNNING.getCode());

        return convert(companyInfoRepository.save(saveCompany));
    }

    @Override
    public CompanyApproveDTO createCompanyInfoByRecommend(CompanyRecommendSignForm recommendSignForm) {
        if(!isAuditPassUser(recommendSignForm.getOpenId())){
            log.error("【企业推荐注册】推荐人信息有误！");
            throw new BuckmooException(ResultEnum.RECOMMEND_PUSHER_ERROR);
        }

        CompanyApproveDTO companyApproveDTO = createOrUpdateInfo(recommendSignForm);

        RecommendSign recommendSign = new RecommendSign();
        recommendSign.setSignOpenId(recommendSignForm.getOpenId());
        recommendSign.setPushOpenId(recommendSignForm.getRecommendCode());
        recommendSign.setRecommendType(RecommendTypeEnum.COMPANY_RECOMMEND.getCode());
        recommendSignRepository.save(recommendSign);

        //通知后台管理
        webSocket.sendMessage("新的企业注册信息有待审核哟 &/admin/approve/company-list");
        //通知微信用户端
        wechatPushMessageService.companyApproveResultStatus(companyApproveDTO);
        String[] split = settingsRepository.getOne("admin_open_id").getSystemValue().split("#");
        wechatPushMessageService.newUserRegister(split);

        return companyApproveDTO;
    }

    @Override
    public Page<CompanyApproveDTO> getApproveList(Pageable pageable) {
        Page<CompanyInfo> companyInfoPage = companyInfoRepository.findAllByAuditStatusIsNot(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageable);
        List<CompanyApproveDTO> dTOList = CompanyInfoToApproveConvert.convert(companyInfoPage.getContent());
        for(CompanyApproveDTO companyApproveDTO: dTOList){
            companyApproveDTO.setUserBasicInfo(userBasicInfoService.getUserBasicInfoByOpenid(companyApproveDTO.getOpenId()));
        }
        return new PageImpl<>(dTOList, pageable, companyInfoPage.getTotalElements());
    }

    private CompanyApproveDTO convert(CompanyInfo companyInfo){
        if(companyInfo == null) throw new BuckmooException(ResultEnum.PARAM_ERROR);
        CompanyApproveDTO companyApproveDTO = new CompanyApproveDTO();
        BeanUtils.copyProperties(companyInfo, companyApproveDTO);
        UserBasicInfo userBasicInfo = userBasicInfoService.getUserBasicInfoByOpenid(companyInfo.getOpenId());
        if(userBasicInfo == null) throw new BuckmooException(ResultEnum.PARAM_ERROR);
        companyApproveDTO.setUserBasicInfo(userBasicInfo);

        Optional<AuditMark> auditMarkFind = auditMarkRepository.findById(companyInfo.getOpenId());
        if(auditMarkFind.isPresent()){
            AuditMark auditMark = auditMarkFind.get();
            AuditMarkDTO auditMarkDTO = new AuditMarkDTO();
            BeanUtils.copyProperties(auditMark, auditMarkDTO);
            companyApproveDTO.setAuditMarkDTO(auditMarkDTO);
        }else{
            companyApproveDTO.setAuditMarkDTO(AuditMarkDTO.getInitInstance());
        }
        return companyApproveDTO;
    }

    @Override
    public Boolean isAuditPassUser(String openId) {
        Optional<CompanyInfo> companyInfo = companyInfoRepository.findById(openId);
        return companyInfo.filter(info -> AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(info.getAuditStatus())).isPresent();
    }
}
