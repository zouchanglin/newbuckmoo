package live.lslm.newbuckmoo.service;

import live.lslm.newbuckmoo.dto.StudentResumeDTO;
import live.lslm.newbuckmoo.form.StudentResumeForm;

public interface StudentResumeService {
    StudentResumeDTO createOrUpdateResume(StudentResumeForm studentResumeForm);
}
