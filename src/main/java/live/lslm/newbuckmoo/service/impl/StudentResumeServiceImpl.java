package live.lslm.newbuckmoo.service.impl;

import live.lslm.newbuckmoo.dto.StudentResumeDTO;
import live.lslm.newbuckmoo.entity.CategoryInfo;
import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.entity.StudentResume;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.enums.ResultEnum;
import live.lslm.newbuckmoo.enums.UserEducationEnum;
import live.lslm.newbuckmoo.enums.UserSexEnum;
import live.lslm.newbuckmoo.exception.BuckmooException;
import live.lslm.newbuckmoo.form.StudentResumeForm;
import live.lslm.newbuckmoo.repository.CategoryInfoRepository;
import live.lslm.newbuckmoo.repository.StudentInfoRepository;
import live.lslm.newbuckmoo.repository.StudentResumeRepository;
import live.lslm.newbuckmoo.repository.UserBasicInfoRepository;
import live.lslm.newbuckmoo.service.StudentResumeService;
import live.lslm.newbuckmoo.utils.ConstUtilPoll;
import live.lslm.newbuckmoo.utils.EnumUtil;
import live.lslm.newbuckmoo.vo.StudentResumeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class StudentResumeServiceImpl implements StudentResumeService {
    @Autowired
    private StudentResumeRepository resumeRepository;

    @Autowired
    private UserBasicInfoRepository basicInfoRepository;

    @Autowired
    private StudentInfoRepository studentInfoRepository;

    @Autowired
    private CategoryInfoRepository categoryInfoRepository;

    @Override
    public StudentResumeVO getOneResumeByOpenId(String openId) {
        Optional<StudentResume> studentResumeOpt = resumeRepository.findById(openId);
        if(studentResumeOpt.isPresent()){
            StudentResume studentResume = studentResumeOpt.get();
            StudentResumeVO resumeVO = new StudentResumeVO();
            BeanUtils.copyProperties(studentResume, resumeVO);
            resumeVO.setResumeEducationStr(Objects.requireNonNull(EnumUtil.getByCode(studentResume.getResumeEducation(), UserEducationEnum.class)).getMessage());
            resumeVO.setResumeSexStr(Objects.requireNonNull(EnumUtil.getByCode(studentResume.getResumeSex(), UserSexEnum.class)).getMessage());
            resumeVO.setUpdateTimeStr(ConstUtilPoll.dateFormat.format(new Date(studentResume.getUpdateTime())));
            resumeVO.setResumeWorkCategoryStr(categoryInfoRepository.findById(studentResume.getResumeWorkCategory()).orElse(new CategoryInfo()).getCategoryName());
            return resumeVO;
        }
        return new StudentResumeVO();
    }

    @Override
    public StudentResumeVO getOneResumeByStudentId(String studentId) {
        StudentInfo findFirst = studentInfoRepository.findFirstByStudentId(studentId);
        if(findFirst != null){
            String openId = findFirst.getOpenId();
            return getOneResumeByOpenId(openId);
        }
        return new StudentResumeVO();
    }

    @Override
    @Transactional
    public StudentResumeDTO createOrUpdateResume(StudentResumeForm studentResumeForm) {
        StudentResume studentResume = new StudentResume();
        BeanUtils.copyProperties(studentResumeForm, studentResume);
        studentResume.setUpdateTime(System.currentTimeMillis());
        StudentResume studentResumeSaved = resumeRepository.save(studentResume);
        log.info("[简历存储] studentResumeSaved={}", studentResumeSaved);
        return convert(studentResumeSaved);
    }

    /**
     * StudentResume ---> StudentResumeDTO
     */
    private StudentResumeDTO convert(StudentResume studentResume){
        StudentResumeDTO studentResumeDTO = new StudentResumeDTO();
        BeanUtils.copyProperties(studentResume, studentResumeDTO);

        Optional<UserBasicInfo> userBasicInfo = basicInfoRepository.findById(studentResume.getOpenId());
        if(userBasicInfo.isPresent()){
            studentResumeDTO.setUserBasicInfo(userBasicInfo.get());
        }else {
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }

        Optional<StudentInfo> studentInfo = studentInfoRepository.findById(studentResume.getOpenId());
        if(studentInfo.isPresent()){
            studentResumeDTO.setStudentInfo(studentInfo.get());
        }else{
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }

        Optional<CategoryInfo> categoryInfo = categoryInfoRepository.findById(studentResume.getResumeWorkCategory());
        if(categoryInfo.isPresent()){
            studentResumeDTO.setCategoryInfo(categoryInfo.get());
        }else{
            throw new BuckmooException(ResultEnum.PARAM_ERROR);
        }
        return studentResumeDTO;
    }
}