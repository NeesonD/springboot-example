package com.neeson.example.auth.api;

import com.neeson.example.auth.service.WeChatOAuthService;
import com.neeson.example.properties.WeChatLoginProperties;
import org.scribe.builder.api.DefaultApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.oauth.OAuthService;
import org.scribe.utils.OAuthEncoder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/4/2
 * Time: 11:30
 * Description:
 */
public class WeChatApi extends DefaultApi20 {

    @Autowired
    private WeChatLoginProperties weChatLoginProperties;

    @Override
    public String getAccessTokenEndpoint() {
        return weChatLoginProperties.getAccessTokenUrl();
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig oAuthConfig) {
        return String.format(weChatLoginProperties.getAuthorizeUrl(), oAuthConfig.getApiKey(), OAuthEncoder.encode(oAuthConfig.getCallback()));
    }

    @Override
    public OAuthService createService(OAuthConfig oAuthConfig) {
        return new WeChatOAuthService(this, oAuthConfig);
    }
}
