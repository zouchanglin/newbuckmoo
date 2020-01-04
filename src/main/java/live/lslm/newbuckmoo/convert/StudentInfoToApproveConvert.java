package live.lslm.newbuckmoo.convert;

import live.lslm.newbuckmoo.dto.StudentApproveDTO;
import live.lslm.newbuckmoo.entity.StudentInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 学生信息对象转 DTO
 */
public class StudentInfoToApproveConvert {
    public static StudentApproveDTO convert(StudentInfo studentInfo) {
        StudentApproveDTO studentApproveDTO = new StudentApproveDTO();
        BeanUtils.copyProperties(studentInfo, studentApproveDTO);
        return studentApproveDTO;
    }
    public static List<StudentApproveDTO> convert(List<StudentInfo> studentInfoList) {
        return studentInfoList.stream().map(StudentInfoToApproveConvert::convert).collect(Collectors.toList());
    }
}
