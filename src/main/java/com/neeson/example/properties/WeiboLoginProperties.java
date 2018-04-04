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
 * Time: 16:02
 * Description:
 */
@Component
@ConfigurationProperties(prefix = "login")
@PropertySource("classpath:/config/weibo.properties")
@Data
public class WeiboLoginProperties {

    private String authorizeUrl;
    private String accessTokenUrl;
    private String protectedResourceUrl;
    private String appKey;
    private String appSecret;


}
