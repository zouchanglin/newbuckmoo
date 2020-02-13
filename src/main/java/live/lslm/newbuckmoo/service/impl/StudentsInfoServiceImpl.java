package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.dto.AuditMarkDTO;
import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.entity.AuditMark;
import live.lslm.newbuckmoo.entity.RecommendSign;
import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.RecommendTypeEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.StudentAttestationForm;
import live.lslm.newbuckmoo.form.student.StudentRecommendSignForm;
import live.lslm.newbuckmoo.repository.*;
import live.lslm.newbuckmoo.service.StudentsInfoService;
import live.lslm.newbuckmoo.service.WebSocket;
import live.lslm.newbuckmoo.service.WechatPushMessageService;
import live.lslm.newbuckmoo.utils.EnumUtil;
import live.lslm.newbuckmoo.vo.StudentVO;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentsInfoServiceImpl implements StudentsInfoService {
    @Autowired
    private StudentInfoRepository studentRepository;

    @Autowired
    private UserBasicInfoRepository userBasicInfoRepository;

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
    public StudentVO getStudentVOByOpenId(String openId) {
        Optional<StudentInfo> studentInfoOpt = studentRepository.findById(openId);
        if(studentInfoOpt.isPresent()){
            StudentInfo studentInfo = studentInfoOpt.get();
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(studentInfo, studentVO);
            studentVO.setAuditStatusStr(Objects.requireNonNull(EnumUtil.getByCode(studentVO.getAuditStatus(), AuditStatusEnum.class)).getMessage());
            return studentVO;
        }
        return null;
    }

    @Override
    public StudentVO convertToVO(StudentApproveDTO studentApproveDTO) {
        StudentVO studentVO = new StudentVO();
        BeanUtils.copyProperties(studentApproveDTO, studentVO);
        studentVO.setAuditStatusStr(studentApproveDTO.getStatusEnum().getMessage());
        return studentVO;
    }

    @Override
    public StudentApproveDTO getStudentInfoByOpenId(String openId) {
        Optional<StudentInfo> studentInfo = studentRepository.findById(openId);
        if(! studentInfo.isPresent()) return null;
        return studentInfo.map(this::convert).orElse(null);
    }

    @Override
    public Page<StudentApproveDTO> getStudentList(Pageable pageable) {
        Page<StudentInfo> studentInfoPage = studentRepository.findAllByAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageable);
        List<StudentApproveDTO> orderDTOList = convert(studentInfoPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, studentInfoPage.getTotalElements());
    }

    @Override
    public Page<StudentApproveDTO> getApproveList(Pageable pageable) {
        Page<StudentInfo> studentInfoPage = studentRepository.findAllByAuditStatusIsNot(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageable);
        List<StudentApproveDTO> orderDTOList = convert(studentInfoPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, studentInfoPage.getTotalElements());
    }

    @Override
    public StudentApproveDTO rejectedStudentApprove(String openId, String auditRemark) {
        Optional<StudentInfo> findResult = studentRepository.findById(openId);
        if(findResult.isPresent()){
            StudentInfo studentInfo = findResult.get();
            if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(studentInfo.getAuditStatus())){
                throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
            }
            studentInfo.setAuditStatus(AuditStatusEnum.AUDIT_FAILED.getCode());
            StudentInfo saved = studentRepository.save(studentInfo);

            saveAuditRemark(openId, auditRemark);

            return convert(saved);
        }else{
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public StudentApproveDTO passStudentApprove(String openId, String auditRemark) {
        Optional<StudentInfo> findResult = studentRepository.findById(openId);
        if(findResult.isPresent()){
            StudentInfo studentInfo = findResult.get();
            if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(studentInfo.getAuditStatus())){
                throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
            }
            studentInfo.setAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode());
            StudentInfo saved = studentRepository.save(studentInfo);
            log.info("[StudentsInfoServiceImpl] saved={}", saved);

            saveAuditRemark(openId, auditRemark);

            return convert(saved);
        }else{
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
        auditMark.setAuditStuTime(System.currentTimeMillis());
        auditMark.setAuditStuCount(auditMark.getAuditStuCount() + 1);
        auditMark.setStudentMark(auditRemark);
        AuditMark save = auditMarkRepository.save(auditMark);
        log.info("【学生审核结果存储】 {}", save);
    }

    @Override
    public StudentApproveDTO createOrUpdateInfo(StudentAttestationForm studentAttestationForm) {
        String openid = studentAttestationForm.getOpenId();
        Optional<StudentInfo> findStudentRet = studentRepository.findById(openid);
        StudentInfo studentInfo;
        if(findStudentRet.isPresent()){
            //存在
            studentInfo = findStudentRet.get();
            //数据库中存在，但是处于未通过状态才可以继续提交
            //校验是否处于正在审核的状态，未通过审核时才能继续提交
            if(AuditStatusEnum.AUDIT_RUNNING.getCode().equals(studentInfo.getAuditStatus())){
                throw new BuckmooException(ResultEnum.AUDITING_NOT_ALLOWED);
            }
        }else{
            studentInfo = new StudentInfo();
            studentInfo.setOpenId(studentAttestationForm.getOpenId());
        }

        studentInfo.setAuditStatus(AuditStatusEnum.AUDIT_RUNNING.getCode());
        studentInfo.setStudentCertificate(studentAttestationForm.getCertificate());
        studentInfo.setStudentName(studentAttestationForm.getName());
        studentInfo.setStudentId(studentAttestationForm.getNumber());
        studentInfo.setStudentSchool(studentAttestationForm.getSchool());
        studentInfo.setUpdateTime(System.currentTimeMillis());
        return convert(studentRepository.save(studentInfo));
    }

    @Override
    public StudentApproveDTO createStudentInfoByRecommend(StudentRecommendSignForm recommendSignForm) {

        if(!isAuditPassUser(recommendSignForm.getRecommendCode())){
            log.error("【学生推荐注册】推荐人信息有误！");
            throw new BuckmooException(ResultEnum.RECOMMEND_PUSHER_ERROR);
        }

        StudentApproveDTO studentApproveDTO = createOrUpdateInfo(recommendSignForm);
        RecommendSign recommendSign = new RecommendSign();
        recommendSign.setSignOpenId(recommendSignForm.getOpenId());
        recommendSign.setPushOpenId(recommendSignForm.getRecommendCode());
        recommendSign.setRecommendType(RecommendTypeEnum.STUDENT_RECOMMEND.getCode());
        recommendSignRepository.save(recommendSign);

        //通知 Web后台管理
        webSocket.sendMessage("新的学生注册信息有待审核哟 &/admin/approve/student-list");
        //通知学生用户正在审核
        wechatPushMessageService.studentApproveResultStatus(studentApproveDTO);
        //微信通知管理员审核
        String[] split = settingsRepository.getOne("admin_open_id").getSystemValue().split("#");
        wechatPushMessageService.newUserRegister(split);

        return studentApproveDTO;
    }

    /**
     * 两个转换 StudentInfo --> StudentApproveDTO
     */
    private StudentApproveDTO convert(StudentInfo studentInfo) {
        StudentApproveDTO studentApproveDTO = new StudentApproveDTO();
        BeanUtils.copyProperties(studentInfo, studentApproveDTO);
        Optional<UserBasicInfo> userBasicInfo = userBasicInfoRepository.findById(studentInfo.getOpenId());
        if(userBasicInfo.isPresent()){
            studentApproveDTO.setUserBasicInfo(userBasicInfo.get());
        }else{
            throw new BuckmooException(ResultEnum.OPENID_STUDENT_ERROR);
        }

        Optional<AuditMark> auditMarkOptional = auditMarkRepository.findById(studentInfo.getOpenId());
        if(auditMarkOptional.isPresent()){
            AuditMark auditMark = auditMarkOptional.get();
            AuditMarkDTO auditMarkDTO = new AuditMarkDTO();
            BeanUtils.copyProperties(auditMark, auditMarkDTO);
            studentApproveDTO.setAuditMarkDTO(auditMarkDTO);
        }else{
            studentApproveDTO.setAuditMarkDTO(AuditMarkDTO.getInitInstance());
            log.info("【StudentInfo->DTO转换】 暂无审核历史");
        }
        return studentApproveDTO;
    }
    private List<StudentApproveDTO> convert(List<StudentInfo> studentInfoList) {
        return studentInfoList.stream().map(this::convert).collect(Collectors.toList());
    }

    @Override
    public Boolean isAuditPassUser(String openId) {
        Optional<StudentInfo> optionalStudentInfo = studentRepository.findById(openId);
        return optionalStudentInfo.map(studentInfo -> studentInfo.getAuditStatus().equals(AuditStatusEnum.AUDIT_SUCCESS.getCode())).orElse(false);
    }
}