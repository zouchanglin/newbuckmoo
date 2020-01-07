package live.lslm.newbuckmoo.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import live.lslm.newbuckmoo.config.AliyunMessageConfig;
import live.lslm.newbuckmoo.service.SendPhoneMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendPhoneMessageServiceImpl implements SendPhoneMessageService {
    @Autowired
    private AliyunMessageConfig aliyunMessageConfig;

    @Override
    public void sendMsg(String phoneNumber, String msg){
        //DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIAlvA2P2ekeha", "6KIoQD2X33V78JSYbp0wGLaUMUm18l");
        DefaultProfile profile = DefaultProfile.getProfile(aliyunMessageConfig.getRegionId(),
                                                           aliyunMessageConfig.getAccessKeyId(),
                                                           aliyunMessageConfig.getSecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "骊山鹿鸣");
        request.putQueryParameter("TemplateCode", "SMS_172603064");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+ msg +"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}