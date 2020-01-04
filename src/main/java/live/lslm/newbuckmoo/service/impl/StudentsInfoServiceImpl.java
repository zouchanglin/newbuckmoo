package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.enums.AuditStatusEnum;
import live.lslm.newbuckmoo.form.StudentAttestationForm;
import live.lslm.newbuckmoo.repository.StudentInfoRepository;
import live.lslm.newbuckmoo.service.StudentsInfoService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentsInfoServiceImpl implements StudentsInfoService {
    private final StudentInfoRepository studentRepository;

    public StudentsInfoServiceImpl(StudentInfoRepository studentRepository) {
        this.studentRepository = studentRepository;
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

        studentInfo.setAuditStatus(AuditStatusEnum.AUDIT_FUTURE.getCode());
        studentInfo.setStudentCertificate(studentAttestationForm.getCertificate());
        studentInfo.setStudentName(studentAttestationForm.getName());
        studentInfo.setStudentId(studentAttestationForm.getNumber());
        studentInfo.setStudentSchool(studentAttestationForm.getSchool());

        return studentRepository.save(studentInfo);
    }
}
