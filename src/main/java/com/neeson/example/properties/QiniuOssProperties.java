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
 * Time: 19:53
 * Description:
 */
@Component
@ConfigurationProperties(prefix = "oss")
@PropertySource("classpath:/config/qiniu.properties")
@Data
public class QiniuOssProperties {

    private String accessKey;
    private String SecretKey;
    private String bucket;


}
