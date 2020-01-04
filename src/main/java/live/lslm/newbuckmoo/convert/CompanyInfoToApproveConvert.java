package live.lslm.newbuckmoo.convert;

import live.lslm.newbuckmoo.dto.CompanyApproveDTO;
import live.lslm.newbuckmoo.entity.CompanyInfo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyInfoToApproveConvert {

    public static CompanyApproveDTO convert(CompanyInfo companyInfo) {
        CompanyApproveDTO companyApproveDTO = new CompanyApproveDTO();
        BeanUtils.copyProperties(companyInfo, companyApproveDTO);
        return companyApproveDTO;
    }
    public static List<CompanyApproveDTO> convert(List<CompanyInfo> content) {
        return content.stream().map(CompanyInfoToApproveConvert::convert).collect(Collectors.toList());
    }
}
