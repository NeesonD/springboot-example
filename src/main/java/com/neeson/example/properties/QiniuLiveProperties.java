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
 * Time: 20:16
 * Description:
 */
@Component
@ConfigurationProperties(prefix = "live")
@PropertySource("classpath:/config/qiniu.properties")
@Data
public class QiniuLiveProperties {

    private String accessKey;
    private String secretKey;
    private String hubName;
    private String rtmpPublishDomain;
    private String rtmpPlayDomain;
    private String hslPlayDomain;
    private String hdlPlayDomain;
    private String snapShotDomain;

}
