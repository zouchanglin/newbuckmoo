package live.lslm.newbuckmoo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {

    /**
     * AppId
     */
    private String mpAppId;

    /**
     * 开发者秘钥
     */
    private String mpAppSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户秘钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String mchPath;

    /**
     * 支付结果异步通知地址
     */
    private String notifyUrl;

    /**
     * 微信模板消息Id
     */
    private Map<String, String> templateId;
}