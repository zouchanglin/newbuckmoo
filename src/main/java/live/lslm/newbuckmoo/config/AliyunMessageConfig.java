package live.lslm.newbuckmoo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Configuration
@Component
@ConfigurationProperties(prefix = "aliyun")
public class AliyunMessageConfig {
    /**
     * regionId
     */
    public String regionId;

    /**
     * accessKeyId
     */
    public String accessKeyId;

    /**
     * secret
     */
    private String secret;
}