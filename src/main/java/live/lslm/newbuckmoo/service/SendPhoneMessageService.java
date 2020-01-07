package live.lslm.newbuckmoo.service;

public interface SendPhoneMessageService {
    /**
     * 发送手机短信
     * @param phoneNumber 手机号
     * @param msg 验证码
     */
    void sendMsg(String phoneNumber, String msg);
}
