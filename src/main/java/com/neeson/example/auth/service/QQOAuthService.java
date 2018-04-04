package com.neeson.example.auth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.neeson.example.auth.OAuthType;
import com.neeson.example.dto.OAuthUser;
import com.neeson.example.properties.QQLoginProperties;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.*;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/31
 * Time: 14:47
 * Description:
 */
public class QQOAuthService extends OAuth20ServiceImpl implements CustomOAuthService {

    @Autowired
    private QQLoginProperties qqLoginProperties;

    public QQOAuthService(DefaultApi20 api, OAuthConfig config) {
        super(api, config);
    }


    @Override
    public String getOAuthType() {
        return OAuthType.QQ;
    }

    @Override
    public OAuthUser getOAuthUser(Token accessToken) {
        OAuthRequest request = new OAuthRequest(Verb.GET, qqLoginProperties.getProtectedResourceUrl());
        this.signRequest(accessToken, request);
        Response response = request.send();
        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setOAuthType(getOAuthType());
        String body = response.getBody();
        Object result = JSON.parse(body);
        oAuthUser.setOpenId(JSONPath.eval(result, "$.unionid").toString());
        oAuthUser.setUsername(JSONPath.eval(result, "$.nickname").toString());
        oAuthUser.setSex(JSONPath.eval(result, "$.sex").toString());
        oAuthUser.setUserImg(JSONPath.eval(result, "$.headimgurl").toString());
        return oAuthUser;
    }
}
