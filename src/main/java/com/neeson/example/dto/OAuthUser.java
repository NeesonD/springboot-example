package com.neeson.example.dto;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/30
 * Time: 19:16
 * Description:
 */
@Data
public class OAuthUser {

    private String username;
    private String userImg;
    private String sex;
    private String oAuthType;
    private String openId;


}
