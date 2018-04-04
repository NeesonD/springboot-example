package com.neeson.example.auth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.neeson.example.auth.OAuthType;
import com.neeson.example.dto.OAuthUser;
import com.neeson.example.properties.WeiboLoginProperties;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.*;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/4/2
 * Time: 20:31
 * Description:
 */
public class SinaWeiboOAuthService extends OAuth20ServiceImpl implements CustomOAuthService {

    private DefaultApi20 api;
    private OAuthConfig config;

    @Autowired
    private WeiboLoginProperties weiboLoginProperties;

    public SinaWeiboOAuthService(DefaultApi20 api, OAuthConfig config) {
        super(api, config);
    }

    @Override
    public String getOAuthType() {
        return OAuthType.WEIBO;
    }

    @Override
    public OAuthUser getOAuthUser(Token accessToken) {
        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setOAuthType(getOAuthType());
        OAuthRequest request = new OAuthRequest(Verb.GET, weiboLoginProperties.getProtectedResourceUrl());
        request.addQuerystringParameter("access_token",accessToken.getToken());
        request.addQuerystringParameter("uid",JSONPath.eval(JSON.parse(accessToken.getRawResponse()), "$.uid").toString());
        Response response = request.send();
        String body = response.getBody();
        Object result = JSON.parse(body);
        oAuthUser.setOpenId(JSONPath.eval(result, "$.id").toString());
        oAuthUser.setUsername(JSONPath.eval(result, "$.screen_name").toString());
        oAuthUser.setSex(JSONPath.eval(result, "$.gender").toString());
        oAuthUser.setUserImg(JSONPath.eval(result, "$.profile_image_url").toString());
        return oAuthUser;
    }
}
