package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.convert.StudentInfoToApproveConvert;
import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.StudentAttestationForm;
import live.lslm.newbuckmoo.repository.StudentInfoRepository;
import live.lslm.newbuckmoo.service.StudentsInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudentsInfoServiceImpl implements StudentsInfoService {
    @Autowired
    private StudentInfoRepository studentRepository;

    @Override
    public void rejectedStudentApprove(String openid) {
        Optional<StudentInfo> findResult = studentRepository.findById(openid);
        if(findResult.isPresent()){
            StudentInfo studentInfo = findResult.get();
            if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(studentInfo.getAuditStatus())){
                throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
            }
            studentInfo.setAuditStatus(AuditStatusEnum.AUDIT_FAILED.getCode());
            StudentInfo saved = studentRepository.save(studentInfo);
            log.info("[StudentsInfoServiceImpl] saved={}", saved);
        }else{
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public void passStudentApprove(String openid) {
        Optional<StudentInfo> findResult = studentRepository.findById(openid);
        if(findResult.isPresent()){
            StudentInfo studentInfo = findResult.get();
            if(AuditStatusEnum.AUDIT_SUCCESS.getCode().equals(studentInfo.getAuditStatus())){
                throw new BuckmooException(ResultEnum.AUDIT_STATUS_ERROR);
            }
            studentInfo.setAuditStatus(AuditStatusEnum.AUDIT_SUCCESS.getCode());
            StudentInfo saved = studentRepository.save(studentInfo);
            log.info("[StudentsInfoServiceImpl] saved={}", saved);
        }else{
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
    }

    @Override
    public StudentInfo createOrUpdateInfo(StudentAttestationForm studentAttestationForm) {
        String openid = studentAttestationForm.getOpenid();
        Optional<StudentInfo> findStudentRet = studentRepository.findById(openid);
        StudentInfo studentInfo;
        if(findStudentRet.isPresent()){
            //存在
            studentInfo = findStudentRet.get();
        }else{
            studentInfo = new StudentInfo();
            studentInfo.setOpenId(studentAttestationForm.getOpenid());
        }

        studentInfo.setAuditStatus(AuditStatusEnum.AUDIT_RUNNING.getCode());
        studentInfo.setStudentCertificate(studentAttestationForm.getCertificate());
        studentInfo.setStudentName(studentAttestationForm.getName());
        studentInfo.setStudentId(studentAttestationForm.getNumber());
        studentInfo.setStudentSchool(studentAttestationForm.getSchool());
        studentInfo.setUpdateTime(System.currentTimeMillis());
        return studentRepository.save(studentInfo);
    }

    @Override
    public Page<StudentApproveDTO> getApproveList(Pageable pageable) {
        Page<StudentInfo> studentInfoPage = studentRepository.findAllByAuditStatusIsNot(AuditStatusEnum.AUDIT_SUCCESS.getCode(), pageable);
        List<StudentApproveDTO> orderDTOList = StudentInfoToApproveConvert.convert(studentInfoPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, studentInfoPage.getTotalElements());
    }
}
