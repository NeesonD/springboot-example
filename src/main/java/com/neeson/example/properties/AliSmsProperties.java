package com.neeson.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/4/3
 * Time: 15:59
 * Description:
 */
@Component
@Data
@ConfigurationProperties("sms")
@PropertySource("classpath:/config/ali.properties")
public class AliSmsProperties {

    private String accesskey;
    private String accessSecret;
    private String signName;
    private String templateCode;


}
