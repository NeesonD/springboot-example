package com.neeson.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/31
 * Time: 20:06
 * Description:
 */
@Component
@ConfigurationProperties(prefix = "live")
@PropertySource("classpath:/config/ali.properties")
@Data
public class AliLiveProperties {

    private String accesskey;
    private String accessSecret;
    private String appName;
    private String domainName;
    private String regionId;
    private String endPointName;
    private String privateKey;
    private String rtmpPushStream;

}
