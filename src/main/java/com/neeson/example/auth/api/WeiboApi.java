package com.neeson.example.auth.api;

import com.neeson.example.auth.service.SinaWeiboOAuthService;
import org.scribe.builder.api.SinaWeiboApi20;
import org.scribe.model.OAuthConfig;
import org.scribe.oauth.OAuthService;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: neeson
 * Date: 2018/4/3
 * Time: 21:37
 * Description:
 */
public class WeiboApi extends SinaWeiboApi20 {

    @Override
    public OAuthService createService(OAuthConfig oAuthConfig) {
        return new SinaWeiboOAuthService(this, oAuthConfig);
    }

}
