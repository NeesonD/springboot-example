package com.neeson.example.auth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.neeson.example.auth.OAuthType;
import com.neeson.example.dto.OAuthUser;
import com.neeson.example.properties.WeChatLoginProperties;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.*;
import org.scribe.oauth.OAuth20ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/4/2
 * Time: 16:37
 * Description:
 */
public class WeChatOAuthService extends OAuth20ServiceImpl implements CustomOAuthService {

    private DefaultApi20 api;
    private OAuthConfig config;

    @Autowired
    private WeChatLoginProperties weChatLoginProperties;

    public WeChatOAuthService(DefaultApi20 api, OAuthConfig config) {
        super(api, config);
        this.api = api;
        this.config = config;
    }

    @Override
    public Token getAccessToken(Token requestToken, Verifier verifier) {
        OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
        request.addQuerystringParameter("appid",config.getApiKey());
        request.addQuerystringParameter("secret",config.getApiSecret());
        request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
        Response response = request.send();
        String body = response.getBody();
        Object result = JSON.parse(body);
        return new Token(JSONPath.eval(result, "$.access_token").toString(), "", body);
    }


    @Override
    public String getOAuthType() {
        return OAuthType.WECHAT;
    }

    @Override
    public OAuthUser getOAuthUser(Token accessToken) {
        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setOAuthType(getOAuthType());
        OAuthRequest request = new OAuthRequest(Verb.GET,weChatLoginProperties.getProtectedResourceUrl());
        request.addQuerystringParameter("access_token",accessToken.getToken());
        request.addQuerystringParameter("openid",JSONPath.eval(JSON.parse(accessToken.getRawResponse()), "$.openid").toString());
        Response response = request.send();
        String body = response.getBody();
        Object result = JSON.parse(body);
        oAuthUser.setOpenId(JSONPath.eval(result, "$.unionid").toString());
        oAuthUser.setUsername(JSONPath.eval(result, "$.nickname").toString());
        oAuthUser.setSex(JSONPath.eval(result, "$.sex").toString());
        oAuthUser.setUserImg(JSONPath.eval(result, "$.headimgurl").toString());
        return oAuthUser;
    }
}
