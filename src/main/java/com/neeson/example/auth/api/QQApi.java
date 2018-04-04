package com.neeson.example.auth.api;

import com.neeson.example.auth.service.QQOAuthService;
import com.neeson.example.properties.QQLoginProperties;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/3/31
 * Time: 14:16
 * Description:
 */
public class QQApi extends DefaultApi20 {

    @Autowired
    private QQLoginProperties qqLoginProperties;



    @Override
    public String getAccessTokenEndpoint() {
        return qqLoginProperties.getAccessTokenUrl();
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig oAuthConfig) {
        if (oAuthConfig.hasScope()) {
            return String.format(qqLoginProperties.getScopedAuthorizeUrl(), oAuthConfig.getApiKey(), OAuthEncoder.encode(oAuthConfig.getCallback()), OAuthEncoder.encode(oAuthConfig.getScope()));
        } else {
            return String.format(qqLoginProperties.getAuthorizeUrl(), oAuthConfig.getApiKey(), OAuthEncoder.encode(oAuthConfig.getCallback()));
        }
    }
    @Override
    public OAuthService createService(OAuthConfig oAuthConfig) {
        return new QQOAuthService(this, oAuthConfig);
    }
}
