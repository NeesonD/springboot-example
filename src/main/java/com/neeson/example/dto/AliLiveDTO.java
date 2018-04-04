package com.neeson.example.dto;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/28
 * Time: 21:49
 * Description:
 */
@Data
public class AliLiveDTO {

    private Integer userId;
    private String domainName;
    private String appName;
    private String streamName;

    private String startTime;
    private String endTime;

}
