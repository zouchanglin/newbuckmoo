package live.lslm.newbuckmoo.service;

import javax.servlet.http.HttpServletResponse;

public interface AdminService {
    /**
     * 管理员登陆
     * @param adminId Id
     * @param adminPass 密码
     * @return 登录是否成功
     */
    Boolean adminLogin(String adminId, String adminPass, HttpServletResponse httpResponse);
}