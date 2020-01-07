package live.lslm.newbuckmoo.controller.develop;

import live.lslm.newbuckmoo.entity.CompanyInfo;
import live.lslm.newbuckmoo.entity.SchoolClubInfo;
import live.lslm.newbuckmoo.entity.StudentInfo;
import live.lslm.newbuckmoo.entity.UserBasicInfo;
import live.lslm.newbuckmoo.repository.CompanyInfoRepository;
import live.lslm.newbuckmoo.repository.SchoolClubInfoRepository;
import live.lslm.newbuckmoo.repository.StudentInfoRepository;
import live.lslm.newbuckmoo.service.UserBasicInfoService;
import live.lslm.newbuckmoo.utils.ChinsesNameUtils;
import live.lslm.newbuckmoo.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 模拟数据生成控制器
 */
@Slf4j
@Controller
@RequestMapping("/develop")
public class AnalogDataGenerator {
    @Autowired
    private StudentInfoRepository repository;

    @Autowired
    private UserBasicInfoService userBasicInfoService;

    @Autowired
    private CompanyInfoRepository companyInfoRepository;

    @Autowired
    private SchoolClubInfoRepository schoolClubInfoRepository;

    @GetMapping("index")
    public ModelAndView getIndex(Map<String, Object> map){
        return new ModelAndView("develop/simulate-data", map);
    }

    @GetMapping("generator-student")
    public ModelAndView generatorStudent(@RequestParam(value = "number" ,defaultValue = "30") Integer number, Map<String, Object> map){
        for (int i = 0; i < number; i++) {
            String openId = KeyUtil.genUniqueKey();
            String studentId = KeyUtil.genUniqueKey();
            UserBasicInfo userBasicInfo = new UserBasicInfo();
            userBasicInfo.setOpenId(openId);
            userBasicInfo.setUserName(ChinsesNameUtils.getChineseName());
            userBasicInfo.setUserGrade((i + 100) % 20);
            userBasicInfo.setUserSex(i%2 + 1);
            userBasicInfo.setUserIcon("http://thirdwx.qlogo.cn/mmopen/vi_32/bxVEQxwmOLibgHtYurJxvW0yicXLVcTCUiaDQDqibEyoIKwS7ZRdOsZL02RibF79vdNt6GgFKMr4fuDNV8T7X3ficTfg/132");
            userBasicInfo.setUserCity("陕西西安");
            userBasicInfo.setUserPhone(KeyUtil.genUniqueKey());
            userBasicInfoService.updateOrCreateUserBasic(userBasicInfo);

            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setOpenId(openId);
            studentInfo.setStudentId(studentId);
            if(i%3 == 0){
                studentInfo.setStudentSchool("西安工程大学");
            }else if(i%3 == 1){
                studentInfo.setStudentSchool("西安科技大学");
            }else{
                studentInfo.setStudentSchool("陕西科技大学");
            }
            studentInfo.setAuditStatus(i % 3);
            studentInfo.setStudentName(ChinsesNameUtils.getChineseName());
            studentInfo.setStudentCertificate("https://s2.ax1x.com/2020/01/05/lBrMPU.png");
            repository.save(studentInfo);
        }
        map.put("msg", "添加"+number+"条数据");
        map.put("url", "admin/approve/student-list");
        return new ModelAndView("common/success", map);
    }


    @GetMapping("generator-company")
    public ModelAndView generatorCompany(@RequestParam(value = "number", defaultValue = "30") Integer number, Map<String, Object> map){
        for (int i = 0; i < number; i++) {
            String openId = KeyUtil.genUniqueKey();
            UserBasicInfo userBasicInfo = new UserBasicInfo();
            userBasicInfo.setOpenId(openId);
            userBasicInfo.setUserName(ChinsesNameUtils.getChineseName());
            userBasicInfo.setUserGrade((i + 100) % number);
            userBasicInfo.setUserSex(i%2 + 1);
            userBasicInfo.setUserIcon("http://thirdwx.qlogo.cn/mmopen/vi_32/bxVEQxwmOLibgHtYurJxvW0yicXLVcTCUiaDQDqibEyoIKwS7ZRdOsZL02RibF79vdNt6GgFKMr4fuDNV8T7X3ficTfg/132");
            userBasicInfo.setUserCity("陕西西安");
            userBasicInfo.setUserPhone(KeyUtil.genUniqueKey());
            userBasicInfoService.updateOrCreateUserBasic(userBasicInfo);

            CompanyInfo companyInfo = new CompanyInfo();
            companyInfo.setOpenId(openId);
            companyInfo.setAuditStatus(i%3);
            String chineseName = ChinsesNameUtils.getChineseName();
            if(i%2==0){
                companyInfo.setCompanyName(chineseName+"责任有限公司");
            }else{
                companyInfo.setCompanyName(chineseName+"股份有限公司");
            }
            companyInfo.setUpdateTime(System.currentTimeMillis());
            companyInfo.setCompanyOwnerName(chineseName);
            companyInfo.setCompanyDesc(chineseName + "是一个懂学生的移动互联网综合实践平台，旗下有课外兼职、品牌活动和研学旅行三大产品");
            companyInfo.setCompanyCertificate("https://s2.ax1x.com/2020/01/06/lsHg1g.png");
            companyInfo.setCompanyId(KeyUtil.genUniqueKey());
            companyInfoRepository.save(companyInfo);
        }
        map.put("msg", "添加"+number+"条数据");
        map.put("url", "admin/approve/company-list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("generator-club")
    public ModelAndView generatorClub(@RequestParam(value = "number", defaultValue = "30") Integer number, Map<String, Object> map){
        for (int i = 0; i < number; i++) {
            String openId = KeyUtil.genUniqueKey();
            UserBasicInfo userBasicInfo = new UserBasicInfo();
            userBasicInfo.setOpenId(openId);
            userBasicInfo.setUserName(ChinsesNameUtils.getChineseName());
            userBasicInfo.setUserGrade((i + 100) % number);
            userBasicInfo.setUserSex(i%2 + 1);
            userBasicInfo.setUserIcon("http://thirdwx.qlogo.cn/mmopen/vi_32/bxVEQxwmOLibgHtYurJxvW0yicXLVcTCUiaDQDqibEyoIKwS7ZRdOsZL02RibF79vdNt6GgFKMr4fuDNV8T7X3ficTfg/132");
            userBasicInfo.setUserCity("陕西西安");
            userBasicInfo.setUserPhone(KeyUtil.genUniqueKey());
            userBasicInfoService.updateOrCreateUserBasic(userBasicInfo);

            SchoolClubInfo schoolClubInfo = new SchoolClubInfo();
            schoolClubInfo.setOpenId(openId);
            schoolClubInfo.setClubCode(KeyUtil.genVerifyKey());
            String chineseName = ChinsesNameUtils.getChineseName();
            schoolClubInfo.setClubDesc(chineseName + "是一个懂学生的社团，旗下有课外兼职、品牌活动和研学旅行三大产品");
            schoolClubInfo.setClubName(chineseName+"社团");
            schoolClubInfo.setUpdateTime(System.currentTimeMillis());
            schoolClubInfo.setOwnerName(chineseName);
            if(i%3 == 0){
                schoolClubInfo.setSchoolName("西安工程大学");
            }else if(i%3 == 1){
                schoolClubInfo.setSchoolName("西安科技大学");
            }else{
                schoolClubInfo.setSchoolName("陕西科技大学");
            }
            schoolClubInfo.setAuditStatus(i%2);
            schoolClubInfoRepository.save(schoolClubInfo);
        }
        map.put("msg", "添加"+number+"条数据");
        map.put("url", "admin/approve/club-list");
        return new ModelAndView("common/success", map);
    }
}