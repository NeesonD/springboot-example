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
 * Time: 17:48
 * Description:
 */
@Component
@ConfigurationProperties("oss")
@PropertySource("classpath:/config/ali.properties")
@Data
public class AliOssProperties {

    private String accesskey;
    private String accessSecret;
    private String endpoint;

}
