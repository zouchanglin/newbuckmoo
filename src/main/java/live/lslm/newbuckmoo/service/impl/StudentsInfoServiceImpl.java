package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.StudentAttestationForm;
import live.lslm.newbuckmoo.repository.StudentInfoRepository;
import live.lslm.newbuckmoo.repository.UserBasicInfoRepository;
import live.lslm.newbuckmoo.service.StudentsInfoService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentsInfoServiceImpl implements StudentsInfoService {
    @Autowired
    private StudentInfoRepository studentRepository;

    @Autowired
    private UserBasicInfoRepository userBasicInfoRepository;

    @Override
    public StudentVO getStudentVOByOpenId(String openId) {
        Optional<StudentInfo> studentInfoOpt = studentRepository.findById(openId);
        if(studentInfoOpt.isPresent()){
            StudentInfo studentInfo = studentInfoOpt.get();
            StudentVO studentVO = new StudentVO();
            BeanUtils.copyProperties(studentInfo, studentVO);
            studentVO.setAuditStatusStr(EnumUtil.getByCode(studentVO.getAuditStatus(), AuditStatusEnum.class).getMessage());
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
    public StudentApproveDTO rejectedStudentApprove(String openid) {
        Optional<StudentInfo> findResult = studentRepository.findById(openid);
        if(findResult.isPresent()){
            StudentInfo studentInfo = findResult.get();
            if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(studentInfo.getAuditStatus())){
                throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
            }
            studentInfo.setAuditStatus(AuditStatusEnum.AUDIT_FAILED.getCode());
            StudentInfo saved = studentRepository.save(studentInfo);
            log.info("[StudentsInfoServiceImpl] saved={}", saved);
            return convert(saved);
        }else{
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public StudentApproveDTO passStudentApprove(String openid) {
        Optional<StudentInfo> findResult = studentRepository.findById(openid);
        if(findResult.isPresent()){
            StudentInfo studentInfo = findResult.get();
            if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(studentInfo.getAuditStatus())){
                throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
            }
            studentInfo.setAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode());
            StudentInfo saved = studentRepository.save(studentInfo);
            log.info("[StudentsInfoServiceImpl] saved={}", saved);

            Optional<UserBasicInfo> userBasicInfo = userBasicInfoRepository.findById(openid);
            if(!userBasicInfo.isPresent()){ throw new BuckmooException(ResultEnum.PARAM_ERROR); }
            return convert(saved);
        }else{
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
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
        return studentApproveDTO;
    }
    private List<StudentApproveDTO> convert(List<StudentInfo> studentInfoList) {
        return studentInfoList.stream().map(this::convert).collect(Collectors.toList());
    }
}